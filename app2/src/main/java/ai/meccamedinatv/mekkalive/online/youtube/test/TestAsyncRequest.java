//package com.walhalla.youtube.test;
//
//import android.os.AsyncTask;
//
//import com.google.api.services.youtube.YouTube;
//
//
//import java.util.ArrayList;
//
//
//public class TestAsyncRequest extends AsyncTask<String, Integer, String> {
//
//    private final String token;
//    private final YouTube youTube;
//    private final String id;
//
//    public TestAsyncRequest(String id, String x, YouTube youTube) {
//        this.id = id;
//        this.token = x;
//        this.youTube = youTube;
//    }
//
//    @Override
//    protected String doInBackground(String... arg) {
//        //ChannelVideosPresenter.getVideosFromChannel(id, token, new ArrayList<>(), youTube);
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//    }
//}