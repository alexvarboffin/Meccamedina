package com.demo.scrapper.ytshorts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShortsRequest {

    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("status")
    @Expose
    public Boolean status;

}


