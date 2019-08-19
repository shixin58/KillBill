package com.bride.demon.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bride.demon.R;
import com.bride.demon.activity.LoginActivity;
import com.bride.ui_lib.BaseFragment;

/**
 * <p>Created by shixin on 2019-08-19.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                LoginActivity.openActivity(getActivity());
                break;
        }
    }
}
