package com.walhalla.ytlib.repository

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAuthIOException
import com.google.api.client.json.GenericJson
import com.google.api.services.youtube.YouTube
import com.walhalla.OrderValue
import com.walhalla.ui.DLog
import com.walhalla.ytlib.domen.Contract
import com.walhalla.ytlib.domen.YT_Entry
import org.w3c.dom.Element
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

abstract class AbstractYoutubeRepository
//        this.executorService = Executors.newSingleThreadExecutor();
//Executors.newFixedThreadPool(2);
    (//protected final ExecutorService executorService;
    val youTube: YouTube, private val googleApiKey: String
) :
    Repository {
    //    public static class SearchRequest{
    //
    //    }
    //SearchListResponse
    //Load PlayList data
    fun searchRequestAsync(
        channelIds: Array<String>,
        mNextPageToken: String?,
        withUserOauth: Boolean,
        mCallback: Repository.Callback<GenericJson>
    ) {
        //                SearchListResponseTask m = new SearchListResponseTask(
//                        youTube, channelIds, pageToken, callback, googleApikey());

        try {
            val search = youTube.search()

            val request = search.list(searchParts)
            request.setOrder(OrderValue.DATE)

            request.setType(listOf("video"))
            request.setEventType("live")

            request.setFields(SearchListResponseTask.fields)
            if (!withUserOauth) {
                request.setKey(googleApiKey)
            }
            request.setQ("mecca online") //Madinah Live
            request.setPageToken(mNextPageToken)
            request.setMaxResults(Const.VIDEO_LIMIT)
            val response = request.execute()


            //                DLog.d("{R}: " + Utils.API_YOUTUBE + "search?part=snippet,id&order=date&\n" +
//                        "type=video&\n" +
//                        "            fields=nextPageToken,pageInfo(totalResults),items(id(videoId),snippet(title,thumbnails,publishedAt))&\n" +
//                        "            key=" + this.googleApiKey + "&\n" +
//                        "            channelId=" + channelId
//                        + "&pageToken=&maxResults=" + VIDEO_LIMIT);
            if (response != null) {
                //getNextPageToken();
                //DLog.d("{a}"+response.getItems().get(0).getKind());
                //DLog.d("{a}"+response.getItems().get(0).);
                mCallback.onMessageRetrieved(response)
            }
        } catch (e: GoogleAuthIOException) {
            //e instanceof UserRecoverableAuthIOException
            //e instanceof GoogleAuthIOException

            DLog.handleException(e)
            mCallback.onRetrievalFailed("", e)
        } catch (e: Exception) {
            DLog.handleException(e)
            mCallback.onRetrievalFailed("", e)
        }
        //executorService.submit(() -> { });
    }

    val searchParts: List<String>
        get() {
            val part: MutableList<String> =
                ArrayList()
            part.add("id")
            part.add("snippet")
            return part
        }


    fun getEntryArray(channel: String): List<YT_Entry>? {
        var result: List<YT_Entry>? = null
        try {
            val url = Const.RSS_URL + channel
            DLog.d(url)


            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc = builder.parse(URL(url).openStream())


            doc.documentElement.normalize()


            //System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            //XMLUtils.printDocument(doc, System.out);
            val arr = (doc.firstChild as Element).getElementsByTagName(Contract.Entry.TAG_ENTRY)
            //YT_Entry.fetchEntryOne(arr.item(0));
            result = YT_Entry().fetchEntryArr(arr)
        } catch (e: Exception) {
            DLog.handleException(e)
        }
        return result
    }

    fun getEntryArrayAsync(channel: String, callback: Repository.Callback<List<YT_Entry>?>?) {
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
        val arr = getEntryArray(channel)
        callback?.onMessageRetrieved(arr)
    }


    fun playListResponse(
        withUserOauth: Boolean, googleApikey: String?, playListId: String?, mNextPageToken: String?,
        mCallback: Repository.Callback<GenericJson>
    ) {
        //            BatchRequest btch = new BatchRequest(transport, credential);
//            list.queue(btch, new JsonBatchCallback<PlaylistItemListResponse>() {
//                @Override
//                public void onSuccess(PlaylistItemListResponse response, HttpHeaders responseHeaders) throws IOException {
//                    DLog.d(response);
//
//
//                }
//
//                @Override
//                public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) throws IOException {
//                    DLog.d(e.getMessage());
//                }
//            });

        try {
            DLog.d("000000000000000000000")
            val part: MutableList<String> = ArrayList()
            //"snippet,id"
            part.add("snippet")
            part.add("id")
            val request = youTube.playlistItems().list(part)
            request.setFields(
                "nextPageToken,pageInfo(totalResults)" +
                        ",items(snippet(title,description,thumbnails,publishedAt,resourceId(videoId)))"
            )
            if (!withUserOauth) {
                request.setKey(googleApikey)
            }
            request.setPlaylistId(playListId)
            request.setPageToken(mNextPageToken)
            request.setMaxResults(Const.VIDEO_LIMIT)

            //==============================================================================
            val response = request.execute()
            if (response != null) {
                mCallback.onMessageRetrieved(response)
                //                    if (BuildConfig.DEBUG) {
//                        DLog.d("response: " + response.toPrettyString());
//                    }
            }
        } catch (e: Exception) {
            DLog.handleException(e)
            mCallback.onRetrievalFailed("909", e)
        }
    }
}
