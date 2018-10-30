package com.bride.demon.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bride.baselib.BaseFragment;
import com.bride.demon.R;
import com.bride.demon.widget.MyView;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A placeholder fragment containing a simple view.
 */
public class TestPlatformFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_platform, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        final MyView myView = getView().findViewById(R.id.my_view);
        myView.setI(MyView.COUNT);
        myView.invalidate();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int i = myView.getI();
                Log.i("TimerTask", i+"");
                if(i>0) {
                    myView.setI(--i);
                    myView.postInvalidate();
                }else {
                    timer.cancel();
                }
            }
        }, 1000, 1000);
    }
}
