package com.ps.news.data.vos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pyaesone on 11/24/18
 */
public class SourceVO {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public SourceVO() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
