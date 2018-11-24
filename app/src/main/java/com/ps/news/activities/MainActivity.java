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
import com.ps.news.persistence.NewsContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>, NewsItemDelegate {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);

        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.accent_color));
        toolbar.setTitleTextColor(getResources().getColor(R.color.primary_color));

        NewsModel.getInstance().startLoadingNewsList(getBaseContext());

        vpNewsList.setEmptyData("Loading...");
        rvNewsList.setEmptyView(vpNewsList);
        rvNewsList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        newsListAdapter = new NewsListAdapter(getApplicationContext(), this);
        rvNewsList.setAdapter(newsListAdapter);

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.ControllerSmartScroll() {
            @Override
            public void onListEndReached() {
//                Snackbar.make(rvNewsList, "Loading data...", Snackbar.LENGTH_LONG).show();
                NewsModel.getInstance().loadMoreNews(getApplicationContext());
            }
        });
        rvNewsList.addOnScrollListener(mSmartScrollListener);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NewsModel.getInstance().forceRefreshNews(getBaseContext());
            }
        });

        getSupportLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
    }

    @Override
    public void onTapNews() {
        Intent intent = NewsDetailActivity.newIntent(getBaseContext(), "");
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsDataLoaded(RestApiEvents.NewsListDataLoadedEvent newsListDataLoadedEvent) {
        if (newsListDataLoadedEvent.getLoadedNewsList() == null && newsListDataLoadedEvent.getLoadedNewsList().isEmpty()) {
            vpNewsList.setEmptyData("No News.");
        } else {
//            newsListAdapter.appendNewData(newsListDataLoadedEvent.getLoadedNewsList());
        }
//        swipeRefreshLayout.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorInvokingAPI(RestApiEvents.ErrorInvokingAPIEvent event) {
        Snackbar.make(rvNewsList, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
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
        if (data != null && data.moveToFirst()) {
            List<NewsVO> newsList = new ArrayList<>();
            do {
                NewsVO newsVO = NewsVO.parseFromCursor(getBaseContext(), data);
                newsList.add(newsVO);
            } while (data.moveToNext());

//            mView.displayNewsList(newsList);
            newsListAdapter.setNewData(newsList);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
