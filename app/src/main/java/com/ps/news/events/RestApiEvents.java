package com.ps.news.events;

import android.content.Context;

import com.ps.news.data.vos.NewsVO;

import java.util.List;

/**
 * Created by pyaesone on 9/20/18
 */
public class RestApiEvents {

    public static class EmptyResponseEvent {

    }

    public static class ErrorInvokingAPIEvent {
        private String errorMsg;

        public ErrorInvokingAPIEvent(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }

    public static class CategoryNewsListErrorInvokingAPIEvent {
        private String errorMsg;

        public CategoryNewsListErrorInvokingAPIEvent(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }

    public static class NewsListDataLoadedEvent {
//        private int loadedPageIndex;
        private List<NewsVO> loadedNewsList;
        private Context context;

        public NewsListDataLoadedEvent(List<NewsVO> newsList, Context context) {
//            this.loadedPageIndex = loadedPageIndex;
            this.loadedNewsList = newsList;
            this.context = context;
        }

//        public int getLoadedPageIndex() {
//            return loadedPageIndex;
//        }


        public List<NewsVO> getLoadedNewsList() {
            return loadedNewsList;
        }

        public Context getContext() {
            return context;
        }
    }

    public static class CategoryNewsListDataLoadedEvent {
        //        private int loadedPageIndex;
        private List<NewsVO> loadedNewsList;
        private Context context;

        public CategoryNewsListDataLoadedEvent(List<NewsVO> newsList, Context context) {
//            this.loadedPageIndex = loadedPageIndex;
            this.loadedNewsList = newsList;
            this.context = context;
        }

//        public int getLoadedPageIndex() {
//            return loadedPageIndex;
//        }


        public List<NewsVO> getLoadedNewsList() {
            return loadedNewsList;
        }

        public Context getContext() {
            return context;
        }
    }
}
