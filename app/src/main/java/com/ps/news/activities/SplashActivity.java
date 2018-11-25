package com.ps.news.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.ps.news.R;

/**
 * Created by pyaesone on 11/25/18
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

//        SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                Intent intent = MainActivity.newIntent(getApplicationContext());
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}
