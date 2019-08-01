package com.bride.baselib.net;

/**
 * <p>Created by shixin on 2019/4/2.
 */
public class UrlParams {
    private StringBuilder stringBuilder = new StringBuilder();

    public UrlParams(String url) {
        stringBuilder.append(url).append('?');
    }

    public UrlParams put(String key, String value) {
        if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length()-1) != '?') {
            stringBuilder.append('&');
        }
        stringBuilder.append(key).append('=').append(value);
        return this;
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
