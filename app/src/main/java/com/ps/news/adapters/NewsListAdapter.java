package com.ps.news.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ps.news.R;
import com.ps.news.data.vos.NewsVO;
import com.ps.news.delegates.NewsItemDelegate;
import com.ps.news.viewholders.NewsViewHolder;

/**
 * Created by pyaesone on 11/24/18
 */
public class NewsListAdapter extends BaseRecyclerAdapter<NewsViewHolder, NewsVO> {

    private NewsItemDelegate mNewsItemDelegate;

    public NewsListAdapter(Context context, NewsItemDelegate newsItemDelegate) {
        super(context);
        mNewsItemDelegate = newsItemDelegate;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_news, parent, false);

        return new NewsViewHolder(view, mNewsItemDelegate);
    }

//    @Override
//    public void onBindViewHolder(NewsViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 7;
//    }
}
