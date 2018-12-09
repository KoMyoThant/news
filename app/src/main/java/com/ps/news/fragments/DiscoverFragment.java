package com.ps.news.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ps.news.R;
import com.ps.news.activities.NewsDetailActivity;
import com.ps.news.activities.SplashActivity;
import com.ps.news.adapters.NewsListAdapter;
import com.ps.news.components.EmptyViewPod;
import com.ps.news.components.rvset.SmartRecyclerView;
import com.ps.news.components.rvset.SmartScrollListener;
import com.ps.news.data.vos.NewsVO;
import com.ps.news.events.RestApiEvents;
import com.ps.news.mvp.presenters.NewsListPresenter;
import com.ps.news.mvp.views.NewsListView;
import com.ps.news.persistence.NewsContract;
import com.ps.news.utils.AppConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pyaesone on 11/26/18
 */
public class DiscoverFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>, NewsListView {

    private static final int NEWS_LOADER_ID = 1001;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.rv_news_list)
    SmartRecyclerView rvNewsList;

    @BindView(R.id.vp_news_list)
    EmptyViewPod vpNewsList;

    private NewsListAdapter newsListAdapter;
    private SmartScrollListener mSmartScrollListener;

    private NewsListPresenter mPresenter;

    public static DiscoverFragment newInstance() {
        DiscoverFragment discoverFragment = new DiscoverFragment();
        return discoverFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_discover, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new NewsListPresenter(this);
        mPresenter.onStartLoadingNewsList(view.getContext());

        vpNewsList.setEmptyData("Loading...");
        rvNewsList.setEmptyView(vpNewsList);
        rvNewsList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        newsListAdapter = new NewsListAdapter(view.getContext(), mPresenter);
        rvNewsList.setAdapter(newsListAdapter);

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.ControllerSmartScroll() {
            @Override
            public void onListEndReached() {
                Snackbar snackbar = Snackbar.make(rvNewsList, "Loading data...", Snackbar.LENGTH_LONG);

                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                        snackbar.getView().getLayoutParams();
                params.setMargins(0, 0, 0, 150);
                snackbar.getView().setLayoutParams(params);
                snackbar.show();

                mPresenter.onLoadMoreNews(getContext());
            }
        });
        rvNewsList.addOnScrollListener(mSmartScrollListener);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onForceRefresh(getContext());
            }
        });

        getActivity().getSupportLoaderManager().initLoader(AppConstants.NEWS_LIST_LOADER_ID, null, this);

        return view;
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

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                snackbar.getView().getLayoutParams();
        params.setMargins(0, 0, 0, 150);
        snackbar.getView().setLayoutParams(params);
        snackbar.show();

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

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getContext(),
                NewsContract.NewsEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mPresenter.onDataLoaded(getContext(), data);
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
        Intent intent = NewsDetailActivity.newIntent(getContext(), newsVO.getNewsUrl());
        startActivity(intent);
    }
}
