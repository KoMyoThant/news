package com.ps.news.data.vos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pyaesone on 11/24/18
 */
public class NewsVO {

    @SerializedName("source")
    private SourceVO source;

    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("url")
    private String newsUrl;

    @SerializedName("urlToImage")
    private String newsImage;

    @SerializedName("publishedAt")
    private String publishedDate;

    @SerializedName("content")
    private String content;

    public NewsVO() { }

    public SourceVO getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getContent() {
        return content;
    }
}
