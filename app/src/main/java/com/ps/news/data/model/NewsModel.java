package com.ps.news.data.model;

import android.content.Context;

import com.ps.news.data.vos.NewsVO;
import com.ps.news.events.RestApiEvents;
import com.ps.news.network.NewsDataAgent;
import com.ps.news.network.NewsDataAgentImpl;
import com.ps.news.utils.AppConstants;
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

    private NewsDataAgent haulioJobDataAgent;

    private List<NewsVO> newsList;

    public static NewsModel getInstance() {
        if (objInstance == null) {
            objInstance = new NewsModel();
        }
        return objInstance;
    }

    public NewsModel() {
        haulioJobDataAgent = NewsDataAgentImpl.getInstance();
        newsList = new ArrayList<>();

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    public void startLoadingJobList(Context context) {
        haulioJobDataAgent.loadNews(RestapiConstants.API_KEY,"10","1","us",context);
    }

    public List<NewsVO> getJobList() {
        return newsList;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsDataLoaded(RestApiEvents.NewsListDataLoadedEvent newsListDataLoadedEvent) {
        newsList.addAll(newsListDataLoadedEvent.getLoadedNewsList());
    }
}
