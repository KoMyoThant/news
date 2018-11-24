package com.ps.news.data.model;

import android.content.Context;

import com.ps.news.data.vos.NewsVO;
import com.ps.news.events.RestApiEvents;
import com.ps.news.network.NewsDataAgent;
import com.ps.news.network.NewsDataAgentImpl;
import com.ps.news.utils.ConfigUtils;
import com.ps.news.utils.RestapiConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pyaesone on 11/24/18
 */
public class NewsModel {

    private static NewsModel objInstance;

    private NewsDataAgent newsDataAgent;

    private List<NewsVO> newsList;

    private ConfigUtils configUtils;

    public static NewsModel getInstance() {
        if (objInstance == null) {
            objInstance = new NewsModel();
        }
        return objInstance;
    }

    private NewsModel() {
        newsDataAgent = NewsDataAgentImpl.getInstance();
        newsList = new ArrayList<>();

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    public void startLoadingNewsList(Context context) {
        //TODO to move place
        configUtils = new ConfigUtils(context);
        newsDataAgent.loadNews(RestapiConstants.API_KEY, "10", configUtils.loadPageIndex(), "us", context);
    }

    public void loadMoreNews(Context context) {
        int pageIndex = configUtils.loadPageIndex();
        newsDataAgent.loadNews(RestapiConstants.API_KEY, "10", pageIndex, "us", context);
    }

    public List<NewsVO> getJobList() {
        return newsList;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsDataLoaded(RestApiEvents.NewsListDataLoadedEvent newsListDataLoadedEvent) {
//        newsList.addAll(newsListDataLoadedEvent.getLoadedNewsList());
        configUtils.savePageIndex(configUtils.loadPageIndex() + 1);
    }
}
