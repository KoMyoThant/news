package com.ps.news.data.model;

import android.content.ContentValues;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.ps.news.NewsApp;
import com.ps.news.data.vos.NewsVO;
import com.ps.news.data.vos.SourceVO;
import com.ps.news.events.RestApiEvents;
import com.ps.news.network.NewsDataAgent;
import com.ps.news.network.NewsDataAgentImpl;
import com.ps.news.persistence.NewsContract;
import com.ps.news.utils.ConfigUtils;
import com.ps.news.utils.NetworkUtils;
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

    private List<NewsVO> categoryNewsList;

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
        categoryNewsList = new ArrayList<>();

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    public void startLoadingNewsList(Context context) {
        //TODO to move place
        configUtils = ConfigUtils.getInstance(context);
        newsDataAgent.loadNews(RestapiConstants.API_KEY, "20", configUtils.loadPageIndex(), "us", context);

    }

    public void loadMoreNews(Context context) {
        int pageIndex = configUtils.loadPageIndex();
        newsDataAgent.loadNews(RestapiConstants.API_KEY, "10", pageIndex, "us", context);
    }

    public void forceRefreshNews(Context context) {
        newsList = new ArrayList<>();
        configUtils.savePageIndex(1);
        startLoadingNewsList(context);
    }

    public List<NewsVO> getNewsList() {
        return newsList;
    }

    public void startLoadingCategoryNewsList(Context context, String categoryName) {
        //TODO to move place
        if (configUtils == null) {
            configUtils = ConfigUtils.getInstance(context);
        }
        newsDataAgent.loadCategoryNews(RestapiConstants.API_KEY, "20", configUtils.loadPageIndex(), categoryName, context);

    }

    public void loadMoreCategoryNews(Context context, String categoryName) {
        int categoryPageIndex = configUtils.loadCategoryPageIndex();
        newsDataAgent.loadCategoryNews(RestapiConstants.API_KEY, "10", categoryPageIndex, categoryName, context);
    }

    public void forceRefreshCategoryNews(Context context, String categoryName) {
        categoryNewsList = new ArrayList<>();
        configUtils.saveCategoryPageIndex(1);
        startLoadingCategoryNewsList(context, categoryName);
    }

    public List<NewsVO> getCategoryNewsList() {
        return categoryNewsList;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsDataLoaded(RestApiEvents.NewsListDataLoadedEvent newsListDataLoadedEvent) {
        newsList.addAll(newsListDataLoadedEvent.getLoadedNewsList());
        configUtils.savePageIndex(configUtils.loadPageIndex() + 1);


//        ContentValues[] newsCVs = new ContentValues[newsListDataLoadedEvent.getLoadedNewsList().size()];
//        List<ContentValues> sourceCVList = new ArrayList<>();
//
//        for (int index = 0; index < newsCVs.length; index++) {
//            NewsVO newsVO = newsListDataLoadedEvent.getLoadedNewsList().get(index);
//            newsCVs[index] = newsVO.parseToContentValues();
//
//            SourceVO sourceVO = newsVO.getSource();
//            sourceCVList.add(sourceVO.parseToContentValues());
//        }
//
//        int insertedRowCount = newsListDataLoadedEvent.getContext().getContentResolver().bulkInsert(NewsContract.NewsEntry.CONTENT_URI, newsCVs);
//        Log.d(NewsApp.TAG, "insertedRowCount : " + insertedRowCount);
//
//        int insertedSources = newsListDataLoadedEvent.getContext().getContentResolver().bulkInsert(NewsContract.SourceEntry.CONTENT_URI,
//                sourceCVList.toArray(new ContentValues[0]));
//        Log.d(NewsApp.TAG, "insertedSources :" + insertedSources);
        saveNewsData(newsListDataLoadedEvent.getContext(), newsListDataLoadedEvent.getLoadedNewsList());
    }

    private void saveNewsData(Context context, List<NewsVO> newsList) {
        //Logic to save the data in Persistence Layer
        ContentValues[] newsCVs = new ContentValues[newsList.size()];
        List<ContentValues> sourceCVList = new ArrayList<>();

        for (int index = 0; index < newsCVs.length; index++) {
            NewsVO news = newsList.get(index);
            newsCVs[index] = news.parseToContentValues();

            SourceVO sourceVO = news.getSource();
            sourceCVList.add(sourceVO.parseToContentValues());
        }

        int insertedSources = context.getContentResolver().bulkInsert(NewsContract.SourceEntry.CONTENT_URI,
                sourceCVList.toArray(new ContentValues[0]));
        Log.d(NewsApp.TAG, "insertedSources : " + insertedSources);

        int insertedNews = context.getContentResolver().bulkInsert(NewsContract.NewsEntry.CONTENT_URI,
                newsCVs);
        Log.d(NewsApp.TAG, "insertedNews : " + insertedNews);
    }

}
