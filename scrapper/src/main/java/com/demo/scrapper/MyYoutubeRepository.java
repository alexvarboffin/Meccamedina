package com.demo.scrapper;

import static com.walhalla.ytlib.repository.SearchListResponseTask.fields;

import android.content.Context;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAuthIOException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.walhalla.OrderValue;
import com.walhalla.ui.DLog;
import com.walhalla.ytlib.repository.AbstractYoutubeRepository;
import com.walhalla.ytlib.repository.Repository;

import java.util.Collections;
import java.util.List;


public class MyYoutubeRepository extends AbstractYoutubeRepository {

    private boolean withUserOauth = false;

    public MyYoutubeRepository(YouTube youTube, String googleApiKey) {
        super(youTube, googleApiKey);
    }

    public void scrapShorts(Context context, String query, Repository.Callback<SearchListResponse> mCallback) {

        try {
            YouTube.Search search = youTube.search();

            YouTube.Search.List request = search.list(getSearchParts());
            request.setOrder(OrderValue.VIEW_COUNT);
            //request.setOrder(OrderValue.DATE);
            request.setQ(query); // Ваш запрос
            request.setType(Collections.singletonList("video"));
            request.setFields(fields);
            request.setVideoDuration("short"); // Ограничение по длительности (до 60 секунд)

            request.setMaxResults(30L);

            if (!withUserOauth) {
                request.setKey(Const.GOOGLE_API_KEY);
            }

            SearchListResponse response = request.execute();
            List<SearchResult> results = response.getItems();

            //results.size() == MaxResults

            //"pageInfo":{"totalResults":1000000}
            DLog.d("============" + response + "@@@" + response.getPageInfo());

            for (SearchResult result : results) {
                DLog.d("https://www.youtube.com/watch?v=" + result.getId().getVideoId());
                DLog.d("Title: " + result.getSnippet().getTitle());
                DLog.d("Published at: " + result.getSnippet().getPublishedAt());
                DLog.d("-----------------------------------");
            }
            mCallback.onMessageRetrieved(response);
        } catch (GoogleAuthIOException e) {
            //e instanceof UserRecoverableAuthIOException
            //e instanceof GoogleAuthIOException
            DLog.handleException(e);
            mCallback.onRetrievalFailed("", e);
        } catch (Exception e) {
            DLog.handleException(e);
            mCallback.onRetrievalFailed("", e);
        }
        ///
    }


    //https://ytshorts.savetube.me/2hsgth
    //...


}