package com.demo.scrapper.ytshorts;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.walhalla.ui.DLog;

import java.util.List;

public class Data {

    @SerializedName("audio_formats")
    @Expose
    public List<AudioFormat> audioFormats;
    @SerializedName("default_selected")
    @Expose
    public Integer defaultSelected;
    @SerializedName("duration")
    @Expose
    public Integer duration;
    @SerializedName("durationLabel")
    @Expose
    public String durationLabel;
    @SerializedName("fromCache")
    @Expose
    public Boolean fromCache;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("key")
    @Expose
    public String key;
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
    @SerializedName("thumbnail_formats")
    @Expose
    public List<ThumbnailFormat> thumbnailFormats;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("titleSlug")
    @Expose
    public String titleSlug;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("video_formats")
    @Expose
    public List<VideoFormat> videoFormats;

    public VideoFormat getBestVideoFormatUrl() {

        int m = -1;
        VideoFormat tmp = null;
        for (VideoFormat format : videoFormats) {
            DLog.d("[v] "+format.url);
            if (format.quality > m && !TextUtils.isEmpty(format.url)) {
                m = format.quality;
                tmp = format;
            }
        }
        return tmp;
    }
}