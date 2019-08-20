package com.bride.demon.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bride.demon.R;
import com.bride.demon.activity.BlankActivity;
import com.bride.demon.activity.PlatformActivity;
import com.bride.demon.module.video.activity.AudioRecordActivity;
import com.bride.demon.module.video.activity.LiveCameraActivity;
import com.bride.demon.module.video.activity.VideoViewActivity;
import com.bride.ui_lib.BaseFragment;

/**
 * Serializable, Parcelable, Gson PK
 * <p>Created by shixin on 2019/4/10.
 */
public class NotificationsFragment extends BaseFragment {

    private boolean blankClicked = false;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            blankClicked = savedInstanceState.getBoolean("blankClicked");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("blankClicked", blankClicked);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_base:
                BlankActivity.Companion.openActivity(getActivity());
                blankClicked = true;
                break;
            case R.id.button_custom_view:
                PlatformActivity.Companion.openActivityForResult(this, 1);
                break;
            case R.id.button_texture_view:
                LiveCameraActivity.openActivity(getActivity());
                break;
            case R.id.button_audio_record:
                AudioRecordActivity.openActivity(getActivity());
                break;
            case R.id.btn_video:
                VideoViewActivity.openActivity(getActivity());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 接收返回
        Log.i("Victor", "requestCode "+requestCode);
    }
}
