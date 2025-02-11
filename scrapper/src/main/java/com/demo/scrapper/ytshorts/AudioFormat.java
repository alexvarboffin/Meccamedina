package com.demo.scrapper.ytshorts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AudioFormat {

    @SerializedName("label")
    @Expose
    public String label;
    @SerializedName("quality")
    @Expose
    public Integer quality;
    @SerializedName("url")
    @Expose
    public Object url;

}