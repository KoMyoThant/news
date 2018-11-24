package com.ps.news.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ps.news.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pyaesone on 11/24/18
 */
public class NewsDetailActivity extends BaseActivity {

    public static final String NEWS_URL = "NEWS_URL";

    @BindView(R.id.wb_news_details)
    WebView wbNewsDetails;

//    @BindView(R.id.progress_bar)
//    ProgressBar progressBar;

    public static Intent newIntent(Context context, String newsUrl) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(NEWS_URL, newsUrl);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this, this);


        wbNewsDetails.getSettings().setJavaScriptEnabled(true);
//        wbNewsDetails.loadUrl("http://www.facebook.com");

        wbNewsDetails.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl("http://www.google.com");
                return true;
            }
        });
        wbNewsDetails.loadUrl("http://www.google.com");


    }

//    public class myWebClient extends WebViewClient {
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            // TODO Auto-generated method stub
//            super.onPageStarted(view, url, favicon);
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            // TODO Auto-generated method stub
//            progressBar.setVisibility(View.VISIBLE);
//            view.loadUrl(url);
//            return true;
//
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            // TODO Auto-generated method stub
//            super.onPageFinished(view, url);
//
//            progressBar.setVisibility(View.GONE);
//        }
//    }
}