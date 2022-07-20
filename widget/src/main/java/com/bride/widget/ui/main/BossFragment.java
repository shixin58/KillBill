package com.bride.widget.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bride.ui_lib.BaseFragment;
import com.bride.widget.R;
import com.bride.widget.databinding.FragmentBossBinding;
import com.bride.widget.model.Boss;

/**
 * <p>Created by shixin on 2019-04-18.
 */
public class BossFragment extends BaseFragment {

    private BossViewModel mViewModel;
    private FragmentBossBinding mViewDataBinding;

    public static BossFragment newInstance() {
        return new BossFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BossViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_boss, container, false);
        return mViewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.setViewModel(mViewModel);

        mViewModel.bossLiveData.observe(getViewLifecycleOwner(), new Observer<Boss>() {
            @Override
            public void onChanged(Boss boss) {
                mViewDataBinding.setBoss(boss);
            }
        });
    }
}
