package ai.meccamedinatv.mekkalive.online.repository

import ai.meccamedinatv.mekkalive.online.config.Const
import android.content.Context
import com.google.api.client.json.GenericJson
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.VideoListResponse
import com.walhalla.ui.DLog
import com.walhalla.ytlib.repository.AbstractYoutubeRepository
import com.walhalla.ytlib.repository.Repository
import java.io.IOException

public class YoutubeRepository(context: Context, youTube: YouTube) : AbstractYoutubeRepository(
    youTube, Const.GOOGLE_API_KEY
) {
    private val current_key: MutableList<String> = ArrayList()

    init {
        //const val GOOGLE_API_KEY = "AIzaSyDC-s6Fm1Enu5DYUA-t6VVRCFTaoam7_QU"
        current_key.add(Const.GOOGLE_API_KEY)
        //this.current_key.add("AIzaSyAdtS_eBQasDv7FLozslHqG8vBaUe9KBoU");
    }


    fun getVideoDataAsync(type: Int, callback: Repository.Callback<*>?) {
        //executorService.submit(new GetVideoData(type, callback));
    }


    //DEFAULT_DURATION "00:00";
    fun getDuration(videoIds: List<String?>?) {
//        //String ids = TextUtils.join(",", videoIds);
//        DLog.d("@@videoIds@@" + videoIds + "@@@");
//        getDurationAsync(videoIds, new Repository.AdvertCallback<VideoListResponse>() {
//            @Override
//            public void onMessageRetrieved(VideoListResponse data) {
//                getViewState().haveResultView();
//                //DLog.d("getDurationSuccessResult: " + data);
//                List<Video> items = data.getItems();
//
//                if (items.isEmpty() || data.isEmpty()) {
//                    if (mIsAppFirstLaunched && HomePresenter.this.uiList.isEmpty()) {
//                        getViewState().noResultView();
//                    }
//                    getViewState().disableLoadMore("777777777777777");
//                } else {
//
//
//                    DLog.d("@@@_SIZE_@@" + uiList.size());
//
//                    for (int i = 0; i < items.size(); i++) {
//
//
//                        Video video = items.get(i);
//
//                        //VideoContentDetails contentDetails = video.getContentDetails();
//
//                        String videoId = video.getId();
//                        String title = video.getSnippet().getTitle();
//                        String description = video.getSnippet().getDescription();
//                        String channelTitle = video.getSnippet().getChannelTitle();
//                        String publishedAt = video.getSnippet().getPublishedAt().toString();
//                        String duration = video.getContentDetails().getDuration();
//                        BigInteger viewCount = video.getStatistics().getViewCount();
//                        boolean embeddable = video.getStatus().getEmbeddable();
//
//                        //Update
//                        ListEntryUI current = HomePresenter.this.uiList.get(i);
//                        current.videoId = videoId;
//                        current.title = title;
//                        current.description = description;
//                        current.channelTitle = channelTitle;
//                        current.publishedAt = publishedAt;
//
//                        current.viewCount = viewCount;
//                        current.embeddable = embeddable;
//                        current.setDuration(Utils.getTimeFromString(duration));
//                        //fail ....
//                        // java.lang.RuntimeException: Can't create handler inside thread that has not called
//                        // Looper.prepare()
//
//                        //Update data
//                        HomePresenter.this.uiList.add(current);
//                    }
//                    DLog.d("@@@_SIZE_@@" + uiList.size());
//                    if (noneNullState()) {
//                        getViewState().swapRecyclerView(uiList, position);
//                    }
//
//                }
//                mIsStillLoading = true;
//                //mTempVideoData.clear();
//                //mTempVideoData = new ArrayList<>();
//            }
//
//            @Override
//            public void onRetrievalFailed(String s, Exception e) {
//                DLog.handleException(e);
//                HomePresenter.this.onRetrievalFailed("448", e);
//            }
//        });
    }
//    fun playListResponseAsync(
//        playListId: String,
//        pageToken: String?,
//        callback: Repository.Callback<GenericJson>
//    ) {
//        mExecutorService.submit {
//            youtubeRepository.playListResponse(
//                Const.WITH_USER_OAUTH,
//                Const.GOOGLE_API_KEY,
//                playListId,
//                pageToken,
//                callback
//            )
//        }
//    }
//
//    //PlaylistItemListResponse
//    fun playListResponseAsync(
//        playListId: String,
//        pageToken: String?,
//        callback: Repository.Callback<GenericJson>
//    ) {
//        executorService.submit(PlaylistListResponseTask(youTube, playListId, pageToken, callback))
//    }

//    fun getDurationAsync(ids: List<String?>?, callback: Repository.Callback<VideoListResponse?>) {
//        executorService.submit { getExtendedVideoDetails(youTube, ids, callback) }
//    }

    fun getExtendedVideoDetails(
        youTube: YouTube,
        ids: List<String?>?,
        callback: Repository.Callback<VideoListResponse?>
    ) {
        try {
            // Определение необходимых частей запроса
            val part: MutableList<String> = ArrayList()
            part.add("snippet")
            part.add("contentDetails")
            part.add("statistics")
            part.add("status")
            part.add("player")
            part.add("topicDetails")
            part.add("recordingDetails")

            // Создание запроса YouTube API
            val request = youTube.videos().list(part)
            // Указание полей, которые нужно включить в ответ
            request.setFields("pageInfo(totalResults),items(id,snippet(title,description,thumbnails,channelTitle,publishedAt),contentDetails(duration,dimension,definition,caption,licensedContent),statistics(viewCount,likeCount,dislikeCount,favoriteCount,commentCount),status(uploadStatus,privacyStatus,license,embeddable,publicStatsViewable),player(embedHtml),topicDetails(topicIds,relevantTopicIds),recordingDetails(location,locationDescription,recordingDate))")

            // Установка ключа API, если не используется OAuth
            if (!Const.WITH_USER_OAUTH) {
                request.setKey(current_key[0])
            }
            // Установка идентификаторов видео для запроса
            request.setId(ids)

            // Выполнение запроса и получение ответа
            val response = request.execute()
            callback.onMessageRetrieved(response)

            // Обработка и вывод данных
//            for (Video video : response.getItems()) {
//                String videoId = video.getId();
//                String title = video.getSnippet().getTitle();
//                String description = video.getSnippet().getDescription();
//                String channelTitle = video.getSnippet().getChannelTitle();
//                String publishedAt = video.getSnippet().getPublishedAt().toString();
//                String duration = video.getContentDetails().getDuration();
//                BigInteger viewCount = video.getStatistics().getViewCount();
//                boolean embeddable = video.getStatus().getEmbeddable();
//
//                // Вывод информации о видео
//                System.out.println("Video ID: " + videoId);
//                System.out.println("Title: " + title);
//                System.out.println("Description: " + description);
//                System.out.println("Channel Title: " + channelTitle);
//                System.out.println("Published At: " + publishedAt);
//                System.out.println("Duration: " + duration);
//                System.out.println("View Count: " + viewCount);
//                System.out.println("Embeddable: " + embeddable);
//
//                // Дополнительно, можно отправить эту информацию куда нужно
//                // sendMessage(2, "Video ID: " + videoId + ", Title: " + title + ", View Count: " + viewCount);
//            }
        } catch (e: Exception) {
            DLog.handleException(e)
            callback.onRetrievalFailed("707", e)
        }
    }


    //    public void getUnderratedLiveBroadcasts(String[] channelIds, String pageToken, AdvertCallback<com.google.api.client.json.GenericJson> callback) {
    //        executorService.submit(() -> {
    //            List<String> part = new ArrayList<>();
    //            part.add("snippet");
    //            part.add("id");
    //            YouTube.LiveBroadcasts.List request;
    //            try {
    //                request = youTube.liveBroadcasts().list(part);
    //                request.setBroadcastStatus("active"); // Get only active broadcasts
    //                LiveBroadcastListResponse response = request.execute();
    //                request.setKey(Const.GOOGLE_API_KEY);
    //                //List<LiveBroadcast> mm = response.getItems();
    //                callback.successResult(response);
    //            } catch (IOException e) {
    //                callback.resultException(e);
    //            }
    //        });
    //    }
    @Throws(IOException::class)
    private fun searchRequest(mChannelId: String) {
        val mNextPageToken = ""
    }
}
