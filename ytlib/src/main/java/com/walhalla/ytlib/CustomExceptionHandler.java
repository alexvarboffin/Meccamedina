//package com.walhalla.meccamedina;
//
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.telephony.TelephonyManager;
//import android.util.Log;
//
//import okhttp3.Call;
//import okhttp3.AdvertCallback;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//import androidx.appcompat.app.AlertDialog;
//
//import com.walhalla.ui.DLog;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.io.Writer;
//
////import okhttp3.MultipartBody;
//
///**
// * Created by combo on 15.12.2016.
// */
//public final class CustomExceptionHandler implements Thread.UncaughtExceptionHandler {
//
//    private static String chatId = "126392885";
//    private static String token = "220535441:AAGSE2J0uJp0X87cxyup4kL9ytybvb78AGk";
//
//    private static String TAG = "@@";
//    static String imei_st;
//    static String ver_st;
//    private static CustomExceptionHandler _instance;
//
//    private static Context m;
//
////    private Application app;
////    private LocalStorage mpm;
//
//    static {
//        _instance = null;
//        imei_st = "";
//        ver_st = "";
//    }
//
//    private Thread.UncaughtExceptionHandler defaultUEH;
//
//    private CustomExceptionHandler(String _tag) {
//        //app = App.getInstance();
//        //mpm = app.getPrefManager();
//
//        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
//        TAG = "EXP|" + _tag;
//    }
//
//    //String uniqueID = UUID.randomUUID().toString();
//
//    public static synchronized CustomExceptionHandler getInstance(Context context) {
//
//        synchronized (CustomExceptionHandler.class) {
//            m = context;
//
//            //imei_st = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
//            if (null == imei_st) imei_st = "not_detect";
////            try {
//////                ver_st = context.getPackageManager()
//////                        .getPackageInfo(context.getPackageName(), 0).versionName;
////            } catch (PackageManager.NameNotFoundException e) {
////                ver_st = "2.1.5";
////            }
//
//            if (null == _instance) {
//                _instance = new CustomExceptionHandler(TAG);
//            }
//        }
//        return _instance;
//    }
//
//    @Override
//    public void uncaughtException(Thread t, Throwable e) {
//        Writer result = new StringWriter();
//        PrintWriter printWriter = new PrintWriter(result);
//        e.printStackTrace(printWriter);
//
//        DLog.d("Thread: " + t + ": CRASHED" + result.toString() + e);
//
//        String stacktrace = result.toString();
//        printWriter.close();
//        try {
//            sendToServer(m, getAndroidVersion() + stacktrace);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        defaultUEH.uncaughtException(t, e);
//    }
//
//
//    public String getAndroidVersion() {
//        String release = Build.VERSION.RELEASE;
//        int sdkVersion = Build.VERSION.SDK_INT;
//        return "\uD83D\uDE48 Android SDK: " + sdkVersion + " (" + release + ") | ";
//    }
//
//    public static void sendToServer(Context context, String stacktrace) throws Exception {
//
//        OkHttpClient client = new OkHttpClient();
//        /*
//        FormBody body = new FormBody.Builder()
//                .add("Content-type", "application/x-www-form-urlencoded")
//                .add("charset", "UTF-8")
//                .add("chat_id", chatId)
//                .add("text", "'kkkkkkkkkkkkkkkkkkkk'")//PACKAGE_NAME + "::" + stacktrace
//
//                .add("peer", chatId)
//                .add("message", "message \tstring \tMessage text")
//                .add("random_id", "ojoijioh")
//
//                .build();
//
//*/
//
//        // Builder urlBuilder = null;
//        // urlBuilder.addQueryParameter("chat_id", chatId);
//        // urlBuilder.addQueryParameter("text", PACKAGE_NAME + "::" + stacktrace);
//
///*
//        urlBuilder.addQueryParameter("imei", imei_st));
//        urlBuilder.addQueryParameter("model", Build.MANUFACTURER + " " + Build.MODEL));
//        urlBuilder.addQueryParameter("os", "" + Build.VERSION.SDK_INT));
//        urlBuilder.addQueryParameter("er", stacktrace));
//        urlBuilder.addQueryParameter("ver", ver_st));*/
//
//
//        //String body = "chat_id=" + chatId + "&text=" + stacktrace;
//        /*Request request = new Request.Builder()
//
//                .method("POST", RequestBody.create(null, new byte[0]))
//                .post(body)
//                //.post(*body)
//                .build();*/
//
//        // data={: 12345, 'text': 'hello friend'}
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(JSON, "data={'chat_id':" + chatId + ", 'text':'"
//                + stacktrace + "'}");
//
////        RequestBody data;
////        data = MultipartBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"),
////                //body
////                "data={'chat_id':" + chatId + ", 'text':'"
////                        + stacktrace + "'}"
////        );
////        RequestBody requestBody1 = RequestBody.create(
////                MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"),                 //body
////                "data={'chat_id':" + chatId + ", 'text':'"
////                        + stacktrace + "'}");
////        Request request = new Request.Builder()
////                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
////                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; rv:50.0) Gecko/20100101 Firefox/50.0") //optional
////                .url("https://api.telegram.org/bot" + token + "/sendMessage")
////                .post(requestBody1)//.post(data) //data ic_call post
////                .build();
//        String body0 = "chat_id=" + chatId + "&text=" + stacktrace;
////        if (parse_mode != null) {
////            body += "&parse_mode=" + parse_mode;
////        }
//
//
//        RequestBody data;
//        data = MultipartBody.create(
//                MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"), body0);
//
//
//        Request request = new Request.Builder()
//                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; rv:50.0) Gecko/20100101 Firefox/50.0") //optional
//                .url("https://api.telegram.org/bot" + token + "/sendMessage")
//                .post(data) //call post
//                .build();
//        client.newCall(request).enqueue(new AdvertCallback() {
//
//            @Override
//            public void onFailure(Call ic_call, IOException e) {
//                Log.d(TAG, "fail");
//            }
//
//            @Override
//            public void onResponse(Call ic_call, Response response) throws IOException {
//                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//                Log.d(TAG, response.body().string());
//            }
//
//        });
//
//
//        //Process.killProcess(Process.myPid());
//        //System.exit(2);
//
////        new AlertDialog.Builder(m)
////                .setTitle(BuildConfig.APPLICATION_ID)
////                .setMessage(stacktrace)
////                .setPositiveButton(android.R.string.ok, null)
////                .show();
//    }
//
//    //https://www.youtube.com/watch?v=09buxer2r-I
//    //https://core.telegram.org/bots/api
//
//
////    private static final OkHttpClient client = new OkHttpClient();
//
//
//}
