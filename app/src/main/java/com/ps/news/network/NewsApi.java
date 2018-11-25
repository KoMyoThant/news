package com.ps.news.network;

import com.ps.news.data.vos.NewsVO;
import com.ps.news.network.responses.GetNewsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pyaesone on 11/24/18
 */
public interface NewsApi {

    @GET("top-headlines?")
    Call<GetNewsResponse> loadAllNewsList(@Query("apikey")String apiKey, @Query("pagesize")String pageSize, @Query("page")String page, @Query("country")String country);

    @GET("top-headlines?")
    Call<GetNewsResponse> loadAllCategoryNewsList(@Query("apikey")String apiKey, @Query("pagesize")String pageSize, @Query("page")String page, @Query("category")String category);

}
