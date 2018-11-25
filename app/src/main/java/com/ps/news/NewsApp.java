package com.ps.news;

import android.app.Application;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

public class NewsApp extends Application {

    public static final String TAG = NewsApp.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();

        SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));

    }
}
