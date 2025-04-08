package com.bride.widget.activity;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bride.baselib.Constants;
import com.bride.baselib.LogUtils;
import com.bride.baselib.PreferenceUtils;
import com.bride.ui_lib.BaseActivity;
import com.bride.widget.R;

/**
 * <p>Created by shixin on 2020/5/3.
 */
public class MultilingualActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multilingual);

        checkLang();
        testCharWidth();
    }

    private void checkLang() {
        RadioGroup rg = findViewById(R.id.rg_language);
        int langInt = PreferenceUtils.getInt(Constants.KEY_LANG, Constants.LANG_ENGLISH);
        if (langInt == Constants.LANG_SIMPLIFIED_CHINESE) {
            rg.check(R.id.rb_chinese);
        } else if (langInt == Constants.LANG_TRADITIONAL_CHINESE) {
            rg.check(R.id.rb_chinese_traditional);
        } else if (langInt == Constants.LANG_KOREAN) {
            rg.check(R.id.rb_korean);
        } else if (langInt == Constants.LANG_SPANISH) {
            rg.check(R.id.rb_spanish);
        }else {
            rg.check(R.id.rb_english);
        }
        rg.setOnCheckedChangeListener((group, checkedId) -> {
            // Switch statement can be replaced with enhanced 'switch'
            if (checkedId == R.id.rb_chinese) {
                setMLangInt(Constants.LANG_SIMPLIFIED_CHINESE);
            } else if (checkedId == R.id.rb_chinese_traditional) {
                setMLangInt(Constants.LANG_TRADITIONAL_CHINESE);
            } else if (checkedId == R.id.rb_korean) {
                setMLangInt(Constants.LANG_KOREAN);
            } else if (checkedId == R.id.rb_spanish) {
                setMLangInt(Constants.LANG_SPANISH);
            }else if (checkedId == R.id.rb_english) {
                setMLangInt(Constants.LANG_ENGLISH);
            }
        });
    }

    private void testCharWidth() {
        Paint p = new Paint();
        p.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16f, getResources().getDisplayMetrics()));
        p.setTypeface(Typeface.DEFAULT);

        TextView tvHelloExtra = findViewById(R.id.tv_hello_extra);
        String[] helloArr = getResources().getStringArray(R.array.spanish);
        StringBuilder helloBuilder = new StringBuilder();
        for (String s : helloArr) {
            LogUtils.INSTANCE.i("MultilingualActivity", s + ": " + p.measureText(s));
            helloBuilder.append(s).append('\n');
        }
        if (helloBuilder.length() > 0) {
            helloBuilder.deleteCharAt(helloBuilder.length() - 1);
        }
        tvHelloExtra.setText(helloBuilder.toString());
    }

    public void onClick(View v) {
        if (v.getId() == R.id.tv_save) {
            PreferenceUtils.putInt(Constants.KEY_LANG, getMLangInt());
            PreferenceUtils.commit();

            finish();
        }
    }
}