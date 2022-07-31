package com.bride.widget.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    private FragmentBossBinding mDataBinding;

    private int id = 0;

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
        mDataBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_boss, container, false);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBinding.setViewModel(mViewModel);
        mDataBinding.setFragment(this);

        mViewModel.bossLiveData.observe(getViewLifecycleOwner(), new Observer<Boss>() {
            @Override
            public void onChanged(Boss boss) {
                mDataBinding.setBoss(boss);
            }
        });
    }

    public void titleBinding(View v) {
        mViewModel.titleField.set("Task-"+id);
        id++;
    }

    public void titleBindingReverse(View v) {
        String title = "Task-"+id;
        mDataBinding.info.setText(title);
        id++;
        Toast.makeText(requireActivity(), mViewModel.titleField.get(), Toast.LENGTH_SHORT).show();
    }
}
