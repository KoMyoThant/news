package com.ps.news.network;

import android.content.Context;

/**
 * Created by pyaesone on 11/24/18
 */
public interface NewsDataAgent {
    void loadNews(String apiKey, String pageSize, int page, String country, Context context);
}
