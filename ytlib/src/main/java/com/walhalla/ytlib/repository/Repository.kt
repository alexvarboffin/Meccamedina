package com.walhalla.ytlib.repository


interface Repository {
    interface Callback<T> {
        fun onMessageRetrieved(data: T)


        fun onRetrievalFailed(s: String, e: Exception?)
    }
    //youtube
    //void getVideoDataAsync(int type, AdvertCallback callback);
    //List<YT_Entry> getEntryArray(String channel);
    //void getEntryArrayAsync(String channel, AdvertCallback<List<YT_Entry>> callback);
}
