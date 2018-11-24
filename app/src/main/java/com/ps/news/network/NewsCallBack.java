package com.ps.news.network;

import com.ps.news.events.RestApiEvents;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pyaesone on 11/24/18
 */
public abstract class NewsCallBack<T extends NewsResponse> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        NewsResponse mmNewsResponse = response.body();
        if(mmNewsResponse==null){
            RestApiEvents.ErrorInvokingAPIEvent errorEvent
                    = new RestApiEvents.ErrorInvokingAPIEvent("No data could be load for now. Please try again later.");
            EventBus.getDefault().post(errorEvent);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        RestApiEvents.ErrorInvokingAPIEvent errorEvent
                = new RestApiEvents.ErrorInvokingAPIEvent(t.getMessage());
        EventBus.getDefault().post(errorEvent);
    }

}
