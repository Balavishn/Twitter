package com.example.balavishnu.twitterjson;

import com.google.gson.annotations.SerializedName;

public class Post {
    private int userid;
    private int id;
    private String title;
    @SerializedName("body")
    private String text;

    public int getUserid() {
        return userid;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
