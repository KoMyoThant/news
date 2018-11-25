package com.ps.news.mvp.presenters;

/**
 * Created by pyaesone on 1/6/18.
 */

public abstract class BasePresenter<T> {

    protected T mView;

    public void onCreate(T view){
        mView = view;
    }

    public abstract void onStart();

    public void onResume(){}

    public void onPause(){}

    public abstract void onStop();

    public void onDestroy(){}
}
