package com.demo.scrapper.ytshorts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThumbnailFormat {

    @SerializedName("label")
    @Expose
    public String label;
    @SerializedName("quality")
    @Expose
    public String quality;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("value")
    @Expose
    public String value;

}
