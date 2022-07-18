package com.bride.demon.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.bride.ui_lib.BaseFragment;
import com.bride.demon.R;
import com.bride.demon.module.video.widget.CustomView;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 自定义View展示1个倒计时countdown。
 * A placeholder fragment containing a simple view.
 */
public class PlatformFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_platform, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View rootView) {
        final CustomView customView = rootView.findViewById(R.id.my_view);
        // 主线程刷新
        customView.setCnt(CustomView.MAX_COUNT);
        customView.invalidate();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int cnt = customView.getCnt();
                if(cnt > 0) {
                    // 工作线程刷新
                    customView.setCnt(--cnt);
                    customView.postInvalidate();
                }else {
                    timer.cancel();
                }
            }
        }, 1000L, 1000L);

        customView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                customView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Log.i("PlatformFragment", "onGlobalLayout() "+customView.getWidth()+" "+customView.getHeight());
            }
        });
    }
}
