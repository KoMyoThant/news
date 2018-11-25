package com.ps.news.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ps.news.R;
import com.ps.news.activities.NewsListInCategoryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pyaesone on 11/26/18
 */
public class CategoryFragment extends BaseFragment {

    @BindView(R.id.cv_business_category)
    CardView cvBusinessCategory;

    @BindView(R.id.cv_entertainment_category)
    CardView cvEntertaimentCategory;

    @BindView(R.id.cv_sport_category)
    CardView cvSportCategory;

    @BindView(R.id.cv_technology_category)
    CardView cvTechnologyCategory;

    @BindView(R.id.cv_health_category)
    CardView cvHealthCategory;

    @BindView(R.id.cv_science_category)
    CardView cvScienceCategory;

    public static CategoryFragment newInstance() {
        CategoryFragment categoryFragment = new CategoryFragment();
        return categoryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_category, container, false);
        ButterKnife.bind(this, view);

        cvBusinessCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NewsListInCategoryActivity.newIntent(getContext(),"Business");
                startActivity(intent);
            }
        });

        cvEntertaimentCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NewsListInCategoryActivity.newIntent(getContext(),"Entertainment");
                startActivity(intent);
            }
        });

        cvSportCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NewsListInCategoryActivity.newIntent(getContext(),"Sport");
                startActivity(intent);
            }
        });

        cvTechnologyCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NewsListInCategoryActivity.newIntent(getContext(),"Technology");
                startActivity(intent);
            }
        });

        cvHealthCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NewsListInCategoryActivity.newIntent(getContext(),"Health");
                startActivity(intent);
            }
        });

        cvScienceCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NewsListInCategoryActivity.newIntent(getContext(),"Science");
                startActivity(intent);
            }
        });

        return view;
    }
}
