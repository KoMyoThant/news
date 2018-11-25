package com.ps.news.mvp.views;

import com.ps.news.data.vos.NewsVO;

import java.util.List;

/**
 * Created by pyaesone on 11/26/18
 */
public interface CategoryNewsListView {

    void displayCategoryNewsList(List<NewsVO> newsList);

    void refreshCategoryNewsList();

    void nevigateToNewsDetail(NewsVO newsVO);
}
