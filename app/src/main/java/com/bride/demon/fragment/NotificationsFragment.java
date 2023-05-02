package com.bride.demon.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bride.demon.activity.BlankActivity;
import com.bride.demon.activity.FlutterFragmentDemoActivity;
import com.bride.demon.databinding.FragmentNotificationsBinding;
import com.bride.demon.module.video.activity.Camera3DActivity;
import com.bride.demon.activity.PlatformActivity;
import com.bride.demon.module.video.activity.AudioRecordActivity;
import com.bride.demon.module.video.activity.DiceActivity;
import com.bride.demon.module.video.activity.GLActivity;
import com.bride.demon.module.video.activity.LiveCameraActivity;
import com.bride.demon.module.video.activity.VideoViewActivity;
import com.bride.ui_lib.BaseFragment;

import io.flutter.embedding.android.FlutterActivity;

/**
 * Serializable, Parcelable, Gson PK
 * <p>Created by shixin on 2019/4/10.
 */
public class NotificationsFragment extends BaseFragment {

    private FragmentNotificationsBinding mBinding;
    private boolean blankClicked = false;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentNotificationsBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("blankClicked", blankClicked);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            blankClicked = savedInstanceState.getBoolean("blankClicked");
        }
        mBinding.setFragment(this);
    }

    public void onClick(View v) {
        if (v == mBinding.buttonBase) {
            BlankActivity.Companion.openActivity(getActivity());
            blankClicked = true;
        } else if (v == mBinding.buttonCustomView) {
            PlatformActivity.Companion.openActivityForResult(this, 1);
        } else if (v == mBinding.buttonTextureView) {
            LiveCameraActivity.openActivity(getActivity());
        } else if (v == mBinding.buttonAudioRecord) {
            AudioRecordActivity.openActivity(getActivity());
        } else if (v == mBinding.btnVideo) {
            VideoViewActivity.openActivity(getActivity());
        } else if (v == mBinding.btnGlsurfaceview) {
            GLActivity.Companion.openActivity(requireActivity());
        } else if (v == mBinding.btnCamera3d) {
            Camera3DActivity.Companion.openActivity(requireActivity());
        } else if (v == mBinding.btnDice) {
            DiceActivity.Companion.openActivity(requireActivity());
        } else if (v == mBinding.btnFlutter) {
//            startActivity(FlutterActivity.createDefaultIntent(requireActivity()));
            /*startActivity(
                    FlutterActivity.withNewEngine()
                            .initialRoute("/")
                            .build(requireActivity())
            );*/
            startActivity(
                    FlutterActivity.withCachedEngine("my_engine_id")
                            .build(requireActivity())
            );
        } else if (v == mBinding.btnFlutterFragment) {
            startActivity(new Intent(getActivity(), FlutterFragmentDemoActivity.class));
        }
    }
}
