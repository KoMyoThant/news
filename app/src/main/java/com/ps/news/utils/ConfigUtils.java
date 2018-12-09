package com.ps.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pyaesone on 12/24/17.
 */

public class ConfigUtils {

    private static final String KEY_PAGE_INDEX = "KEY_PAGE_INDEX";
    private static final String KEY_CATEGORY_PAGE_INDEX = "KEY_CATEGORY_PAGE_INDEX";

    private SharedPreferences mSharedPreferences;

    private static ConfigUtils objInstance;

    public static ConfigUtils getInstance(Context context) {
        if (objInstance == null) {
            objInstance = new ConfigUtils(context);
        }

        return objInstance;
    }

    private ConfigUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences("ConfigUtils", Context.MODE_PRIVATE);
        savePageIndex(1);
        saveCategoryPageIndex(1);
    }

    public void savePageIndex(int pageIndex) {
        mSharedPreferences.edit().putInt(KEY_PAGE_INDEX, pageIndex).apply();
    }

    public int loadPageIndex() {
        return mSharedPreferences.getInt(KEY_PAGE_INDEX, 1);
    }

    public void saveCategoryPageIndex(int categoryPageIndex) {
        mSharedPreferences.edit().putInt(KEY_CATEGORY_PAGE_INDEX, categoryPageIndex).apply();
    }

    public int loadCategoryPageIndex() {
        return mSharedPreferences.getInt(KEY_CATEGORY_PAGE_INDEX, 1);
    }
}
