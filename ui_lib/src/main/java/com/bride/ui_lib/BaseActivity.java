package com.bride.ui_lib;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import com.bride.baselib.PreferenceUtils;

import java.util.Locale;

/**
 * <p>Created by shixin on 2019/3/22.
 */
public class BaseActivity extends AppCompatActivity {
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected Handler getHandler() {
        return mHandler;
    }

    protected void setLanguage() {
        String lang = PreferenceUtils.getString("language", Locale.ENGLISH.getLanguage());
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        if (lang.equals(Locale.CHINESE.getLanguage())) {
            config.setLocale(Locale.CHINESE);
        } else {
            config.setLocale(Locale.ENGLISH);
        }
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(config, dm);
    }
}
