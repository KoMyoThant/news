package com.ps.news.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ps.news.R;
import com.ps.news.utils.StringUtils;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pyaesone on 11/24/18
 */
public class NewsDetailActivity extends BaseActivity {

    public static final String NEWS_URL = "NEWS_URL";

    @BindView(R.id.iv_back_key)
    ImageView ivBackKey;

    @BindView(R.id.tv_news_source_url)
    TextView tvNewsSourceUrl;

    @BindView(R.id.iv_share_key)
    ImageView ivShareKey;

    @BindView(R.id.wb_news_details)
    WebView wbNewsDetails;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private String newsSourceUrl;

    public static Intent newIntent(Context context, String newsUrl) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(NEWS_URL, newsUrl);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this, this);

        newsSourceUrl = getIntent().getStringExtra(NEWS_URL);

        if (!TextUtils.isEmpty(newsSourceUrl)) {
            tvNewsSourceUrl.setText(newsSourceUrl);
        }

        wbNewsDetails.getSettings().setJavaScriptEnabled(true);

        wbNewsDetails.setWebViewClient(new MyWebViewClient());
        wbNewsDetails.getSettings().setLoadsImagesAutomatically(true);

        if (newsSourceUrl != null && !newsSourceUrl.isEmpty()) {
            wbNewsDetails.loadUrl(newsSourceUrl);
        }

        ivBackKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivShareKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(newsSourceUrl)) {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, newsSourceUrl);
                    startActivity(shareIntent);
                }
            }
        });

    }

    public class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            progressBar.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(newsSourceUrl)) {
                try {
                    tvNewsSourceUrl.setText(StringUtils.getHostName(newsSourceUrl));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
