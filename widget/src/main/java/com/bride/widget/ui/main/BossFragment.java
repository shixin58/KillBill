package com.bride.widget.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bride.ui_lib.BaseFragment;
import com.bride.widget.R;
import com.bride.widget.databinding.FragmentBossBinding;
import com.bride.widget.model.Boss;

/**
 * <p>Created by shixin on 2019-04-18.
 */
public class BossFragment extends BaseFragment {

    private BossViewModel mViewModel;
    private FragmentBossBinding mFragmentBossBinding;

    public static BossFragment newInstance() {
        return new BossFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentBossBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_boss, container, false);
        return mFragmentBossBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BossViewModel.class);
        mViewModel.bossLiveData.observe(this, new Observer<Boss>() {
            @Override
            public void onChanged(Boss boss) {
                mFragmentBossBinding.setBoss(boss);
            }
        });
        getActivity().findViewById(R.id.tv_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.doAction();
            }
        });
        mViewModel.resultImageUrl.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mFragmentBossBinding.setViewModel(mViewModel);
            }
        });
        getActivity().findViewById(R.id.tv_load_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.updateScenery();
            }
        });
    }
}
