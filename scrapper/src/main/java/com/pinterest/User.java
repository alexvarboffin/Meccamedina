package com.pinterest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("boards")
    @Expose
    private List<String> boards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getBoards() {
        return boards;
    }

    public void setBoards(List<String> boards) {
        this.boards = boards;
    }

}