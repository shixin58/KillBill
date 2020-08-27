package com.bride.widget.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.bride.baselib.PreferenceUtils;
import com.bride.ui_lib.BaseActivity;
import com.bride.widget.MainActivity;
import com.bride.widget.R;
import com.bride.widget.event.LanguageChangedEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

/**
 * <p>Created by shixin on 2020/5/3.
 */
public class MultilingualActivity extends BaseActivity {

    private Locale mLocale = Locale.ENGLISH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multilingual);

        RadioGroup rg = findViewById(R.id.rg_language);
        String lang = PreferenceUtils.getString("language", Locale.ENGLISH.getLanguage());
        checkLang(rg, lang);
        rg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_chinese:
                    mLocale = Locale.CHINESE;
                    break;
                case R.id.rb_english:
                    mLocale = Locale.ENGLISH;
                    break;
            }
        });
    }

    private void checkLang(RadioGroup rg, String lang) {

        if (lang.equals(new Locale("zh").getLanguage())) {
            rg.check(R.id.rb_chinese);
        } else {
            rg.check(R.id.rb_english);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_save:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                EventBus.getDefault().post(new LanguageChangedEvent());
                PreferenceUtils.putString("language", mLocale.getLanguage());
                PreferenceUtils.commit();
                break;
        }
    }
}
