package com.demo.scrapper.ytshorts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoFormat {

    @SerializedName("default_selected")
    @Expose
    public Integer defaultSelected;
    @SerializedName("height")
    @Expose
    public Integer height;
    @SerializedName("label")
    @Expose
    public String label;
    @SerializedName("quality")
    @Expose
    public Integer quality;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("width")
    @Expose
    public Integer width;
}
