package com.ps.news.mvp.views;

import android.content.Context;

import com.ps.news.data.vos.NewsVO;

import java.util.List;

/**
 * Created by pyaesone on 11/25/18
 */
public interface NewsListView {

    void displayNewsList(List<NewsVO> newsList);

    void refreshNewsList();

    void nevigateToNewsDetail(NewsVO newsVO);

}
