package com.ps.news.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ps.news.R;
import com.ps.news.data.vos.NewsVO;
import com.ps.news.delegates.NewsItemDelegate;

import butterknife.BindView;

/**
 * Created by pyaesone on 11/24/18
 */
public class NewsViewHolder extends BaseViewHolder<NewsVO> {

    @BindView(R.id.iv_news_hero_img)
    ImageView ivNewsHeroImg;

    @BindView(R.id.tv_news_title)
    TextView tvNewsTitle;

    @BindView(R.id.tv_news_source_name)
    TextView tvNewsSourceName;

    @BindView(R.id.tv_published_date)
    TextView tvPublishedDate;

    private NewsItemDelegate mNewsItemDelegate;

    public NewsViewHolder(View itemView, NewsItemDelegate newsItemDelegate) {
        super(itemView);
        mNewsItemDelegate = newsItemDelegate;
    }

    @Override
    public void setData(NewsVO data) {
        if (data != null) {
            mData = data;

            if (mData.getTitle() != null &&!mData.getTitle().isEmpty()) {
                tvNewsTitle.setVisibility(View.VISIBLE);
                tvNewsTitle.setText(mData.getTitle());
            }

            if (mData.getSource().getName() != null && !mData.getSource().getName().isEmpty()){
                tvNewsSourceName.setVisibility(View.VISIBLE);
                tvNewsSourceName.setText(mData.getSource().getName());
            }

            if (mData.getPublishedDate() != null && !mData.getPublishedDate().isEmpty()) {
                tvPublishedDate.setVisibility(View.VISIBLE);
                tvPublishedDate.setText(mData.getPublishedDate().split("T")[0]);
            }

            ivNewsHeroImg.setVisibility(View.VISIBLE);
            if (mData.getNewsImage() != null) {
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.img_placeholder)
                        .centerCrop();
                Glide.with(itemView.getRootView().getContext()).load(mData.getNewsImage()).apply(requestOptions).into(ivNewsHeroImg);
            }

        }
    }

    @Override
    public void onClick(View v) {
        mNewsItemDelegate.onTapNews();
    }
}
