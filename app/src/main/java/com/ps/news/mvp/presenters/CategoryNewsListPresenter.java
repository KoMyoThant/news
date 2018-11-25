package com.ps.news.mvp.presenters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.ps.news.data.model.NewsModel;
import com.ps.news.data.vos.NewsVO;
import com.ps.news.delegates.NewsItemDelegate;
import com.ps.news.events.RestApiEvents;
import com.ps.news.mvp.views.CategoryNewsListView;
import com.ps.news.presenters.BasePresenter;
import com.ps.news.utils.ConfigUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by pyaesone on 11/26/18
 */
public class CategoryNewsListPresenter extends BasePresenter<CategoryNewsListView> implements NewsItemDelegate {

    private CategoryNewsListView mCategoryNewsListView;

    public CategoryNewsListPresenter(CategoryNewsListView categoryNewsListView) {
        mCategoryNewsListView = categoryNewsListView;
    }

    @Override
    public void onTapNews(NewsVO newsVO) {
        mCategoryNewsListView.nevigateToNewsDetail(newsVO);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public void onStartLoadingCategoryNewsList(Context context, String categoryName) {
        NewsModel.getInstance().startLoadingCategoryNewsList(context, categoryName);
    }

    public void onLoadMoreCategoryNews(Context context, String categoryName) {
        NewsModel.getInstance().loadMoreCategoryNews(context, categoryName);
    }

    public void onForceRefresh(Context context, String categoryName) {
        NewsModel.getInstance().forceRefreshCategoryNews(context, categoryName);
    }
}
