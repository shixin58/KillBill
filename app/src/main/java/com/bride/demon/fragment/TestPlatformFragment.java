package com.bride.demon.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bride.baselib.BaseFragment;
import com.bride.demon.R;

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
}
