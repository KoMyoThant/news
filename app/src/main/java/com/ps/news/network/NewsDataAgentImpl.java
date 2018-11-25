package com.ps.news.network;

import android.content.Context;

import com.google.gson.Gson;
import com.ps.news.events.RestApiEvents;
import com.ps.news.network.responses.GetNewsResponse;
import com.ps.news.utils.AppConstants;
import com.ps.news.utils.NetworkUtils;
import com.ps.news.utils.RestapiConstants;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pyaesone on 11/24/18
 */
public class NewsDataAgentImpl implements NewsDataAgent {

    private NewsApi theAPI;

    private static NewsDataAgentImpl objInstance;

    private NewsDataAgentImpl() {
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        // time 60 sec is optimal.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestapiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(okHttpClient)
                .build();

        theAPI = retrofit.create(NewsApi.class);
    }

    public static NewsDataAgentImpl getInstance() {
        if (objInstance == null) {
            objInstance = new NewsDataAgentImpl();
        }

        return objInstance;
    }

    @Override
    public void loadNews(String apiKey, String pageSize, int page, String country, final Context context) {
        if (NetworkUtils.getInstance().checkConnection(context)){
            Call<GetNewsResponse> loadMMNewsCall = theAPI.loadAllNewsList(apiKey, pageSize,String.valueOf(page),country);
            loadMMNewsCall.enqueue(new NewsCallBack<GetNewsResponse>() {
                @Override
                public void onResponse(Call<GetNewsResponse> call, Response<GetNewsResponse> response) {
                    super.onResponse(call,response);
                    GetNewsResponse getNewsResponse = response.body();
                    if (getNewsResponse != null
                            && getNewsResponse.getNewsList().size() > 0) {
                        RestApiEvents.NewsListDataLoadedEvent newsDataLoadedEvent = new RestApiEvents.NewsListDataLoadedEvent( getNewsResponse.getNewsList(),context);
                        EventBus.getDefault().post(newsDataLoadedEvent);
                    }
                }
            });
        }else {
            RestApiEvents.ErrorInvokingAPIEvent errorInvokingAPIEvent = new RestApiEvents.ErrorInvokingAPIEvent("No Internet Connection");
            EventBus.getDefault().post(errorInvokingAPIEvent);
        }

    }
}
