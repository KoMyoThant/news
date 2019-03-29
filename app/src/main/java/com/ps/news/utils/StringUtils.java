package com.ps.news.utils;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by pyaesone on 3/29/19
 */
public class StringUtils {

    public static String getHostName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain;
    }
}
