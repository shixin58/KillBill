package com.bride.demon.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bride.ui_lib.BaseFragment;
import com.bride.demon.R;
import com.bride.demon.module.video.widget.CustomView;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlatformFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_platform, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        final CustomView customView = getView().findViewById(R.id.my_view);
        customView.setI(CustomView.COUNT);
        customView.invalidate();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int i = customView.getI();
                Log.i("TimerTask", i+"");
                if(i>0) {
                    customView.setI(--i);
                    customView.postInvalidate();
                }else {
                    timer.cancel();
                }
            }
        }, 1000, 1000);
    }
}
