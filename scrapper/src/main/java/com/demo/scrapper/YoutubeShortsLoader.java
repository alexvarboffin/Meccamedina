package com.demo.scrapper;

import android.content.Context;

import com.demo.scrapper.ytshorts.ShortsRequest;
import com.demo.scrapper.ytshorts.VideoFormat;
import com.google.gson.Gson;
import com.walhalla.ui.BuildConfig;
import com.walhalla.ui.DLog;
import com.walhalla.ytlib.repository.Repository;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

public class YoutubeShortsLoader {


    public static void videoLoader(Context context, YoutubeVideo youtubeVideo,
                                   Repository.Callback<YoutubeVideo> callback) {

        OkHttpClient client = defClient();

        // Формируем URL
        String url = "https://cdn60.savetube.su/info?url=" + youtubeVideo.getVideoLink();

        // Создаем запрос
        Request request = new Request.Builder()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:132.0) Gecko/20100101 Firefox/132.0")
                .url(url)
                .build();

        try {
            // Выполняем синхронный запрос
            Response response = client.newCall(request).execute();

            // Проверяем успешность ответа
            if (response.isSuccessful() && response.body() != null) {
                // Получаем тело ответа
                ResponseBody responseBody = response.body();
                String json = responseBody.string();

                // Преобразуем JSON в объект ShortsRequest с использованием Gson
                Gson gson = new Gson();
                ShortsRequest shortsRequest = gson.fromJson(json, ShortsRequest.class);

                // Используем объект shortsRequest по необходимости
                // Например:
                DLog.d("ShortsRequest: " + shortsRequest.message);
                DLog.d("ShortsRequest: " + shortsRequest.data.videoFormats);
//                for (VideoFormat format : shortsRequest.data.videoFormats) {
//
//                }
                VideoFormat m = shortsRequest.data.getBestVideoFormatUrl();
                DLog.d("ShortsRequest: " + shortsRequest.status);
                DLog.d("[" + m.quality + "] " + m.url);

                youtubeVideo.download_link = m.url;
                youtubeVideo.title = shortsRequest.data.title;
                youtubeVideo.quality=m.quality;
                callback.onMessageRetrieved(youtubeVideo);

            } else {
                callback.onRetrievalFailed("Request failed: " + response.code(), null);
            }
        } catch (IOException e) {
            callback.onRetrievalFailed("Request failed: " + e.getLocalizedMessage(), e);
        }

    }

    protected static OkHttpClient defClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        return builder.build();
    }
}
