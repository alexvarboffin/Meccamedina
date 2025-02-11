package com.walhalla.ytlib.repository

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAuthIOException
import com.google.api.client.json.GenericJson
import com.google.api.services.youtube.YouTube
import com.walhalla.OrderValue
import com.walhalla.ui.DLog

/**
 * Поиск по каналу по его ид
 * на выходе список видео...
 *
 *
 * Поиск по броадкастам {video and live}, по ключевому слову
 *
 *
 * https://www.googleapis.com/youtube/v3/search?part=snippet,id&order=date&
 * type=video&
 * fields=nextPageToken,pageInfo(totalResults),items(id(videoId),snippet(title,thumbnails,publishedAt))&
 * key=AIzaSyCPfYO0bwuV4Z9zCwZcYctHTmYJqzuPoOU&
 * channelId=UCzE7HcbvyEiS5ea1rVRbPLQ&pageToken=&maxResults=8
 */
class SearchListResponseTask(
    private val youTube: YouTube,
    private val mChannelIds: Array<String>,
    private val mNextPageToken: String,
    private val mCallback: Repository.Callback<GenericJson>,
    private val googleApiKey: String
) {
    //    public void run() {
    //        //loadPlaylistData
    ////            try{
    ////                //Params
    ////                HashMap<String, String> parameters = new HashMap<>();
    ////                parameters.put("part", "snippet");
    ////
    ////
    ////                Subscription subscription = new Subscription();
    ////                SubscriptionSnippet snippet = new SubscriptionSnippet();
    ////                ResourceId resourceId = new ResourceId();
    ////                resourceId.set("channelId", "UC_x5XG1OV2P6uZZ5FSM9Ttw");
    ////                resourceId.set("kind", "youtube#channel");
    ////
    ////                snippet.setResourceId(resourceId);
    ////                subscription.setSnippet(snippet);
    ////
    ////                YouTube.Subscriptions.Insert request = SERVICE.subscriptions()
    ////                        .insert(parameters.get("part"), subscription);
    ////                Subscription response = request.execute();
    ////                DLog.d(response);
    ////
    ////            }catch (Exception e){
    ////                Logger.e(e.getMessage());
    ////            }
    //
    //
    //        broadcasts();
    //    }
    /**
     * Const.VIDEO_LIMIT
     * Const.WITH_USER_OAUTH;
     */
    private fun v1(withUserOauth: Boolean, videoLimit: Long) {
        for (channelId in mChannelIds) {
            try {
                val search = youTube.search()
                val part: MutableList<String> = ArrayList()
                //"id,snippet"
                part.add("id")
                part.add("snippet")

                val request = search.list(part)
                request.setOrder(OrderValue.DATE) //viewCount
                //video,channel,playlist
                request.setType(listOf("video"))
                request.setFields(fields)
                if (!withUserOauth) {
                    request.setKey(this.googleApiKey)
                }
                request.setChannelId(channelId)
                request.setPageToken(this.mNextPageToken)
                request.setMaxResults(videoLimit)
                val response = request.execute()


                //                DLog.d("{R}: " + Utils.API_YOUTUBE + "search?part=snippet,id&order=date&\n" +
//                        "type=video&\n" +
//                        "            fields=nextPageToken,pageInfo(totalResults),items(id(videoId),snippet(title,thumbnails,publishedAt))&\n" +
//                        "            key=" + this.googleApiKey + "&\n" +
//                        "            channelId=" + channelId
//                        + "&pageToken=&maxResults=" + VIDEO_LIMIT);
                if (response != null) {
                    //getNextPageToken();
//                    DLog.d("{a}"+response.getItems().get(0).getKind());
//                    DLog.d("{a}"+response.getItems().get(0).getSnippet().getLiveBroadcastContent());
//                    DLog.d("{a}"+response.getItems().get(0).getSnippet().getPublishedAt());
//                    DLog.d("{a}"+response.getItems().get(0).getSnippet().getChannelTitle());
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
        }
    }

    companion object {
        //"kind":"youtube#searchResult"
        const val fields: String = "nextPageToken,pageInfo(totalResults)" +
                ",kind" +
                ",items(id(videoId)" +  //",kind" +
                ",snippet(title,description,thumbnails,publishedAt))"
    }
}