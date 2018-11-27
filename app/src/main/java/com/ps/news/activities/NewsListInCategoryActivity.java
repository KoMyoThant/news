package com.ps.news.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ps.news.R;
import com.ps.news.adapters.NewsListAdapter;
import com.ps.news.components.EmptyViewPod;
import com.ps.news.components.rvset.SmartRecyclerView;
import com.ps.news.components.rvset.SmartScrollListener;
import com.ps.news.data.vos.NewsVO;
import com.ps.news.events.RestApiEvents;
import com.ps.news.mvp.presenters.CategoryNewsListPresenter;
import com.ps.news.mvp.views.CategoryNewsListView;
import com.ps.news.utils.ConfigUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pyaesone on 11/26/18
 */
public class NewsListInCategoryActivity extends BaseActivity implements CategoryNewsListView {

    public static final String CATEGORY_NAME = "CATEGORY_NAME";


    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.rv_news_list)
    SmartRecyclerView rvNewsList;

    @BindView(R.id.vp_news_list)
    EmptyViewPod vpNewsList;

    private NewsListAdapter newsListAdapter;
    private SmartScrollListener mSmartScrollListener;

    private CategoryNewsListPresenter mPresenter;

    private String categoryName = "";

    public static Intent newIntent(Context context, String categoryName) {
        Intent intent = new Intent(context, NewsListInCategoryActivity.class);
        intent.putExtra(CATEGORY_NAME, categoryName);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list_in_category);
        ButterKnife.bind(this, this);

        categoryName = getIntent().getStringExtra(CATEGORY_NAME);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(categoryName + " News");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mPresenter = new CategoryNewsListPresenter(this);
        mPresenter.onStartLoadingCategoryNewsList(getApplicationContext(), categoryName.toLowerCase());

        vpNewsList.setEmptyData("Loading...");
        rvNewsList.setEmptyView(vpNewsList);
        rvNewsList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        newsListAdapter = new NewsListAdapter(getApplicationContext(), mPresenter);
        rvNewsList.setAdapter(newsListAdapter);

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.ControllerSmartScroll() {
            @Override
            public void onListEndReached() {
                Snackbar.make(rvNewsList, "Loading data...", Snackbar.LENGTH_LONG).show();
                mPresenter.onLoadMoreCategoryNews(getApplicationContext(), categoryName.toLowerCase());
            }
        });
        rvNewsList.addOnScrollListener(mSmartScrollListener);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onForceRefresh(getApplicationContext(), categoryName.toLowerCase());
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
//        mPresenter.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
//        mPresenter.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCategoryNewsDataLoaded(RestApiEvents.CategoryNewsListDataLoadedEvent categoryNewsListDataLoadedEvent) {
        if (categoryNewsListDataLoadedEvent.getLoadedNewsList() == null && categoryNewsListDataLoadedEvent.getLoadedNewsList().isEmpty()) {
            Snackbar snackbar = Snackbar.make(rvNewsList, "No News to load.", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setVisibility(View.GONE);
                }
            }).show();
            swipeRefreshLayout.setRefreshing(false);
        } else {
            ConfigUtils.getInstance(getApplicationContext()).saveCategoryPageIndex(ConfigUtils.getInstance(getApplicationContext()).loadCategoryPageIndex() + 1);
//            HashSet<NewsVO> categoryNewsHashSet = new HashSet<>(categoryNewsListDataLoadedEvent.getLoadedNewsList());
//            List<NewsVO> categoryNewsList = new ArrayList<>(categoryNewsHashSet);
            newsListAdapter.appendNewData(categoryNewsListDataLoadedEvent.getLoadedNewsList());
            swipeRefreshLayout.setRefreshing(false);

//            displayCategoryNewsList(categoryNewsListDataLoadedEvent.getLoadedNewsList());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCategoryNewsErrorInvokingAPI(RestApiEvents.CategoryNewsListErrorInvokingAPIEvent event) {
        Snackbar snackbar = Snackbar.make(rvNewsList, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
            }
        }).show();
        swipeRefreshLayout.setRefreshing(false);
        vpNewsList.setEmptyData(event.getErrorMsg());
    }

    @Override
    public void displayCategoryNewsList(List<NewsVO> newsList) {
        newsListAdapter.setNewData(newsList);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshCategoryNewsList() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void nevigateToNewsDetail(NewsVO newsVO) {
        Intent intent = NewsDetailActivity.newIntent(getBaseContext(), newsVO.getNewsUrl());
        startActivity(intent);
    }


}
