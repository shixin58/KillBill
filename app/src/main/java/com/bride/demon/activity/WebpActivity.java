package com.bride.demon.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bride.baselib.BaseActivity;
import com.bride.demon.R;

/**
 * <p>Created by shixin on 2018/10/22.
 */
public class WebpActivity extends BaseActivity {
    private static final String TAG = WebpActivity.class.getSimpleName();
    public static void openActivity(Context context) {
        Intent intent = new Intent(context, WebpActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webp);
        initView();
    }

    private void initView() {
        ImageView imageView = findViewById(R.id.iv_example);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.actress, options);
        Log.i(TAG, "size = "+options.outWidth+", "+options.outHeight+", "+(getBitmapSize(bitmap)/1024)+"KB");
        Log.i(TAG, "outMimeType = "+options.outMimeType);// image/jpeg
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i(TAG, "outConfig = "+options.outConfig);// ARGB_8888
            Log.i(TAG, "outColorSpace = "+options.outColorSpace);// sRGB IEC61966-2.1 (id=0, model=RGB)
        }
        imageView.setImageBitmap(bitmap);
    }

    private int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = super.onCreateView(name, context, attrs);
        Log.i(TAG, "onCreateView - name["+name
                +"] - view["+(view==null?null:view.getClass().getSimpleName())+"]");
        return view;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = super.onCreateView(parent, name, context, attrs);
        Log.i(TAG, "onCreateView1 - parent["+(parent==null?null:parent.getClass().getSimpleName())+"] - name["+name
                +"] - view["+(view==null?null:view.getClass().getSimpleName())+"]");
        return view;
    }

    @Override
    public View onCreatePanelView(int featureId) {
        View view = super.onCreatePanelView(featureId);
        Log.i(TAG, "onCreatePanelView - " + featureId
                + " - view["+(view==null?null:view.getClass().getSimpleName())+"]");
        return view;
    }
}
