package com.ps.news.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.ps.news.R;
import com.ps.news.adapters.NewsListAdapter;
import com.ps.news.components.EmptyViewPod;
import com.ps.news.components.rvset.SmartRecyclerView;
import com.ps.news.components.rvset.SmartScrollListener;
import com.ps.news.data.model.NewsModel;
import com.ps.news.delegates.NewsItemDelegate;
import com.ps.news.events.RestApiEvents;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NewsItemDelegate {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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

        NewsModel.getInstance().startLoadingJobList(getBaseContext());

        vpNewsList.setEmptyData("Loading...");
        rvNewsList.setEmptyView(vpNewsList);
        rvNewsList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        newsListAdapter = new NewsListAdapter(getApplicationContext(), this);
        rvNewsList.setAdapter(newsListAdapter);

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.ControllerSmartScroll() {
            @Override
            public void onListEndReached() {
                Snackbar.make(rvNewsList, "Loading data...", Snackbar.LENGTH_LONG).show();
            }
        });
        rvNewsList.addOnScrollListener(mSmartScrollListener);
    }

    @Override
    public void onTapNews() {
        Intent intent = NewsDetailActivity.newIntent(getBaseContext(),"");
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsDataLoaded(RestApiEvents.NewsListDataLoadedEvent newsListDataLoadedEvent) {
        if (newsListDataLoadedEvent.getLoadedNewsList() == null && newsListDataLoadedEvent.getLoadedNewsList().isEmpty()) {
            vpNewsList.setEmptyData("No News.");
        } else {
            newsListAdapter.setNewData(newsListDataLoadedEvent.getLoadedNewsList());
        }
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
}
