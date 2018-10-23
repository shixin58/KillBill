package com.bride.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bride.baselib.BaseActivity;
import com.bride.demon.R;

import org.jetbrains.annotations.Nullable;

/**
 * <p>Created by shixin on 2018/10/22.
 */
public class WebpActivity extends BaseActivity {
    public static void openActivity(Context context) {
        Intent intent = new Intent(context, WebpActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webp);
        initView();
    }

    private void initView() {
        ImageView imageView = findViewById(R.id.iv_example1);
        imageView.setImageResource(R.mipmap.actress);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = super.onCreateView(name, context, attrs);
        Log.i("onCreateView", name+": "+view);
        return view;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        if(attrs!=null && attrs.getAttributeCount()>0) {
            for(int i=0;i<attrs.getAttributeCount();i++) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    Log.i("AttributeSet", attrs.getAttributeNamespace(i)+"-"+attrs.getAttributeName(i)+"-"+attrs.getAttributeValue(i));
                }else {
                    Log.i("AttributeSet", attrs.getAttributeName(i)+"-"+attrs.getAttributeValue(i));
                }
            }
        }
        View view = super.onCreateView(parent, name, context, attrs);
        Log.i("onCreateView1", parent+": "+view);
        return view;
    }

    @androidx.annotation.Nullable
    @Override
    public View onCreatePanelView(int featureId) {
        View view = super.onCreatePanelView(featureId);
        Log.i("onCreatePanelView", featureId+": "+view);
        return view;
    }
}
