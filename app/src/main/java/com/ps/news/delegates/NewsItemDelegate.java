package com.ps.news.delegates;

import com.ps.news.data.vos.NewsVO;

/**
 * Created by pyaesone on 11/24/18
 */
public interface NewsItemDelegate {
    void onTapNews(NewsVO newsVO);
}
