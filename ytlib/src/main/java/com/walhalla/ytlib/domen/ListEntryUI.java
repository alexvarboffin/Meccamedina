package com.walhalla.ytlib.domen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;

public class ListEntryUI implements Serializable {

    @SerializedName("id")
    @Expose
    public String videoId;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("duration")
    @Expose
    public String duration;

    @SerializedName("thumbnails")
    @Expose
    public String urlThumbnails;

    @SerializedName("publishedAt")
    @Expose
    public String publishedAt;

    @SerializedName("type")
    @Expose
    public int type;

    public String description = "";
    public String channelTitle = "";

    public BigInteger viewCount = BigInteger.valueOf(0);
    public boolean embeddable = false;

    public ListEntryUI(String id, String title, String duration, String urlThumbnails, String publishedAt, int type) {
        this.videoId = id;
        this.title = title;
        this.duration = duration;
        this.urlThumbnails = urlThumbnails;
        this.publishedAt = publishedAt;
        this.type = type;
    }

    public ListEntryUI() {}

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public String getUrlThumbnails() {
        return urlThumbnails;
    }

    public String getPublishedAt() {
        return publishedAt;
    }


    public void setUrlThumbnails(String urlThumbnails) {
        this.urlThumbnails = urlThumbnails;
    }


    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }


    public void setVideoId(String id) {
        this.videoId = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //Request #2
    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getVideoId() {
        return videoId;
    }

    private boolean active;

    public boolean isActive() {
        return active;
    }


}
