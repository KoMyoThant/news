package com.ps.news.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;
import com.ps.news.persistence.NewsContract;

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

    public ContentValues parseToContentValues() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(NewsContract.NewsEntry.COLUMN_AUTHOR, author);
        contentValues.put(NewsContract.NewsEntry.COLUMN_TITLE, title);
        contentValues.put(NewsContract.NewsEntry.COLUMN_DESCRIPTION, description);
        contentValues.put(NewsContract.NewsEntry.COLUMN_URL, newsUrl);
        contentValues.put(NewsContract.NewsEntry.COLUMN_URL_TO_IMAGE, newsImage);
        contentValues.put(NewsContract.NewsEntry.COLUMN_PUBLISHED_DATE, publishedDate);
        contentValues.put(NewsContract.NewsEntry.COLUMN_CONTENT, content);
        contentValues.put(NewsContract.NewsEntry.COLUMN_SOURCE_ID, source.getId());

        return contentValues;
    }

    public static NewsVO parseFromCursor(Context context, Cursor cursor) {
        NewsVO news = new NewsVO();
        news.author = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_AUTHOR));
        news.title = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_TITLE));
        news.description = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_DESCRIPTION));
        news.newsUrl = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_URL));
        news.newsImage = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_URL_TO_IMAGE));
        news.publishedDate = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_PUBLISHED_DATE));
        news.content = cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_CONTENT));

        news.source = SourceVO.parseFromCursor(cursor);
        return news;
    }
}
