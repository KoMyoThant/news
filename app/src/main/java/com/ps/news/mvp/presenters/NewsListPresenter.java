package com.ps.news.mvp.presenters;

import android.content.Context;
import android.database.Cursor;

import com.ps.news.data.model.NewsModel;
import com.ps.news.data.vos.NewsVO;
import com.ps.news.delegates.NewsItemDelegate;
import com.ps.news.mvp.views.NewsListView;
import com.ps.news.presenters.BasePresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pyaesone on 11/25/18
 */
public class NewsListPresenter extends BasePresenter<NewsListView> implements NewsItemDelegate {

    private NewsListView mNewsListView;

    public NewsListPresenter(NewsListView newsListView) {
        mNewsListView = newsListView;
    }

    @Override
    public void onStart() {
//        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onTapNews(NewsVO newsVO) {
        mNewsListView.nevigateToNewsDetail(newsVO);
    }

    public void onDataLoaded(Context context, Cursor data) {
        if (data != null && data.moveToFirst()) {
            List<NewsVO> newsList = new ArrayList<>();
            do {
                NewsVO newsVO = NewsVO.parseFromCursor(context, data);
                newsList.add(newsVO);
            } while (data.moveToNext());
            mNewsListView.displayNewsList(newsList);
        } else {
            NewsModel.getInstance().startLoadingNewsList(context);
        }
    }

    public void onStartLoadingNewsList(Context context) {
        NewsModel.getInstance().startLoadingNewsList(context);
    }

    public void onLoadMoreNews(Context context) {
        NewsModel.getInstance().loadMoreNews(context);
    }

    public void onForceRefresh(Context context) {
        NewsModel.getInstance().forceRefreshNews(context);
    }


}
