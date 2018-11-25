package com.ps.news.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ps.news.R;
import com.ps.news.adapters.NewsListAdapter;
import com.ps.news.components.EmptyViewPod;
import com.ps.news.components.rvset.SmartRecyclerView;
import com.ps.news.components.rvset.SmartScrollListener;
import com.ps.news.data.model.NewsModel;
import com.ps.news.data.vos.NewsVO;
import com.ps.news.delegates.NewsItemDelegate;
import com.ps.news.events.RestApiEvents;
import com.ps.news.mvp.presenters.NewsListPresenter;
import com.ps.news.mvp.views.NewsListView;
import com.ps.news.persistence.NewsContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>, NewsListView {

    private static final int NEWS_LOADER_ID = 1001;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.rv_news_list)
    SmartRecyclerView rvNewsList;

    @BindView(R.id.vp_news_list)
    EmptyViewPod vpNewsList;

    private NewsListAdapter newsListAdapter;
    private SmartScrollListener mSmartScrollListener;

    private NewsListPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);

        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.accent_color));
        toolbar.setTitleTextColor(getResources().getColor(R.color.primary_color));

        mPresenter = new NewsListPresenter(this);
        mPresenter.onStartLoadingNewsList(getBaseContext());

        vpNewsList.setEmptyData("Loading...");
        rvNewsList.setEmptyView(vpNewsList);
        rvNewsList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        newsListAdapter = new NewsListAdapter(getApplicationContext(), mPresenter);
        rvNewsList.setAdapter(newsListAdapter);

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.ControllerSmartScroll() {
            @Override
            public void onListEndReached() {
//                Snackbar.make(rvNewsList, "Loading data...", Snackbar.LENGTH_LONG).show();
                mPresenter.onLoadMoreNews(getApplicationContext());
            }
        });
        rvNewsList.addOnScrollListener(mSmartScrollListener);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onForceRefresh(getBaseContext());
            }
        });

        getSupportLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsDataLoaded(RestApiEvents.NewsListDataLoadedEvent newsListDataLoadedEvent) {
        if (newsListDataLoadedEvent.getLoadedNewsList() == null && newsListDataLoadedEvent.getLoadedNewsList().isEmpty()) {
            vpNewsList.setEmptyData("No News.");
        } else {
//            newsListAdapter.appendNewData(newsListDataLoadedEvent.getLoadedNewsList());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorInvokingAPI(RestApiEvents.ErrorInvokingAPIEvent event) {
        Snackbar snackbar = Snackbar.make(rvNewsList, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
            }
        }).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mPresenter.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mPresenter.onStop();
        EventBus.getDefault().unregister(this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getApplicationContext(),
                NewsContract.NewsEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mPresenter.onDataLoaded(getBaseContext(), data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public void displayNewsList(List<NewsVO> newsList) {
        newsListAdapter.setNewData(newsList);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshNewsList() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void nevigateToNewsDetail(NewsVO newsVO) {
        Intent intent = NewsDetailActivity.newIntent(getBaseContext(), newsVO.getNewsUrl());
        startActivity(intent);
    }
}
