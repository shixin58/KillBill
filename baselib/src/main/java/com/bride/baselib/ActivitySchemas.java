package com.bride.baselib;

import android.support.annotation.NonNull;

/**
 * <p>Created by shixin on 2018/9/27.
 */
public class ActivitySchemas {
    private final static String SCHEMA_FORMAT = "max://demon/%s";
    public final static String RECYCLER_VIEW_SCHEMA = String.format(SCHEMA_FORMAT, "recycler.view");
    public final static String TEST_TOUCH_SCHEMA = String.format(SCHEMA_FORMAT, "test.touch");

    private StringBuilder url = new StringBuilder();

    public ActivitySchemas(String schema) {
        url.append(schema);
    }

    public ActivitySchemas setParam(@NonNull String key, @NonNull String value) {
        if(url.indexOf("?")<0){
            url.append('?');
        }else {
            url.append('&');
        }
        url.append(key).append('=').append(value);
        return this;
    }

    public ActivitySchemas setParam(@NonNull String key, int value) {
        if(url.indexOf("?")<0){
            url.append('?');
        }else {
            url.append('&');
        }
        url.append(key).append('=').append(value);
        return this;
    }

    public String getUriString() {
        return url.toString();
    }
}
