package com.walhalla.ytlib.domen;



public interface Contract {

    String TAG_FEED = "feed";

    String TAG_LINK = "link";
    String TAG_ID = "id";
    String TAG_YT_CHANNELID = "yt:channelId";
    String TAG_TITLE = "title";
    String TAG_AUTHOR = "author";
    String TAG_PUBLISHED = "published";

    interface Author {
        String TAG_NAME = "name";
        String TAG_URI = "uri";
    }

    interface Entry {

        String TAG_LINK = "link";
        String TAG_ID = "id";
        String TAG_YT_CHANNELID = "yt:channelId";
        String TAG_TITLE = "title";
        String TAG_AUTHOR = "author";

        String TAG_PUBLISHED = "published";
        String TAG_ENTRY = "entry";
        String TAG_YT_VIDEOID = "yt:videoId";
        String TAG_UPDATED = "updated";
        String TAG_MEDIA_GROUP = "media:group";

        interface MediaGroup {
            String TAG_MEDIA_TITLE = "media:title";
            String TAG_MEDIA_CONTENT = "media:content";
            String TAG_MEDIA_THUMBNAIL = "media:thumbnail";
            String TAG_MEDIA_DESCRIPTION = "media:description";
            String TAG_MEDIA_COMMUNITY = "media:community";

            interface MediaCommunity {
                String TAG_MEDIA_STATISTICS = "media:statistics";
                String TAG_MEDIA_STARRATING = "media:starRating";
            }
        }
    }


}
