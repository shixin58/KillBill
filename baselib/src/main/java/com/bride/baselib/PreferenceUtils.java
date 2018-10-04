package com.bride.baselib;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * <p>Created by shixin on 2018/4/7.
 */
public class PreferenceUtils {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void initialize(Context context, String name) {
        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor() {
        if(editor==null) {
            editor = preferences.edit();
        }
        return editor;
    }

    public static String getString(String key) {
        return preferences.getString(key, "");
    }

    public static void putString(String key, String value) {
        getEditor().putString(key, value);
    }

    public static boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public static void putBoolean(String key, boolean value) {
        getEditor().putBoolean(key, value);
    }

    public static void clear() {
        getEditor().clear();
    }

    public static void commit() {
        getEditor().commit();
    }
}
