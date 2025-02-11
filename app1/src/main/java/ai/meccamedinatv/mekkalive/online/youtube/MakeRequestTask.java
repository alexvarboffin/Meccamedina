//package com.walhalla.youtube;
//
//import android.os.AsyncTask;
//import android.text.TextUtils;
//
////import com.google.api.client.extensions.android.http.AndroidHttp;
//import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
////import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.services.youtube.model.Channel;
//import com.google.api.services.youtube.model.ChannelListResponse;
//import com.walhalla.meccamedina.mvp.presenter.HomePresenter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
//
//    private final HomePresenter var0;
//    private com.google.api.services.youtube.YouTube mService = null;
//    private Exception mLastError = null;
//
//    MakeRequestTask(GoogleAccountCredential credential, HomePresenter homePresenter) {
//        this.var0 = homePresenter;
//
//        //HttpTransport transport = AndroidHttp.newCompatibleTransport();
//        HttpTransport TRANSPORT = new NetHttpTransport();
//
//        //JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
//        final JsonFactory jsonFactory = new GsonFactory();
//
//        mService = new com.google.api.services.youtube.YouTube.Builder(
//                TRANSPORT, jsonFactory, credential)
//                .setApplicationName("YouTube Data API Android Quickstart")
//                .build();
//    }
//
//    /**
//     * Background task to call YouTube Data API.
//     *
//     * @param params no parameters needed for this task.
//     */
//    @Override
//    protected List<String> doInBackground(Void... params) {
//
//        List<String> data = null;
//
//        try {
//            data = getDataFromApi();
//        } catch (Exception e) {
//            mLastError = e;
//            this.cancel(true);
//        }
//        return data;
//    }
//
//    /**
//     * Fetch information about the "GoogleDevelopers" YouTube channel.
//     *
//     * @return List of Strings containing information about the channel.
//     * @throws IOException
//     */
//    private List<String> getDataFromApi() throws IOException {
//        // Get a list of up to 10 files.
//        List<String> channelInfo = new ArrayList<String>();
//
//
//        ChannelListResponse result = mService.channels()
//                .list("snippet,contentDetails,statistics")
//                .setForUsername("GoogleDevelopers")
//                .execute();
//
//
//        List<Channel> channels = result.getItems();
//        if (channels != null) {
//            Channel channel = channels.get(0);
//            channelInfo.add("This channel's ID is " + channel.getId() + ". " +
//                    "Its title is '" + channel.getSnippet().getTitle() + ", " +
//                    "and it has " + channel.getStatistics().getViewCount() + " views.");
//        }
//        return channelInfo;
//    }
//
//
//    @Override
//    protected void onPreExecute() {
////            mOutputText.setText("");
//        var0.getViewState().progressDialogShow();
//    }
//
//    @Override
//    protected void onPostExecute(List<String> output) {
//        var0.getViewState().progressDialogHide();
//        if (output == null || output.size() == 0) {
//            var0.getViewState().errorResult("No results returned.");
//        } else {
//            output.add(0, "Data retrieved using the YouTube Data API:");
//            var0.getViewState().successResult(TextUtils.join("\n", output));
//        }
//    }
//
//    @Override
//    protected void onCancelled() {
//        var0.getViewState().progressDialogHide();
//        if (mLastError != null) {
//            var0.resultException("c888",mLastError);
//        } else {
//            var0.getViewState().errorResult("Request cancelled.");
//        }
//    }
//}
