package com.walhalla.ytlib.database;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;

@Keep
public class ListEntryDB implements Serializable {

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

    @SerializedName("description")
    @Expose
    public String description = "";
    @SerializedName("channelTitle")
    @Expose
    public String channelTitle = "";

    //public BigInteger viewCount = BigInteger.valueOf(0);

    @SerializedName("embeddable")
    @Expose
    public boolean embeddable = false;

    public ListEntryDB() {}

    public ListEntryDB(String id, String title, String duration, String urlThumbnails, String publishedAt, int type) {
        this.videoId = id;
        this.title = title;
        this.duration = duration;
        this.urlThumbnails = urlThumbnails;
        this.publishedAt = publishedAt;
        this.type = type;
    }
}
