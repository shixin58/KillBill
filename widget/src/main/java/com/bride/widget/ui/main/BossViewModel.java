package com.bride.widget.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bride.widget.model.Boss;

/**
 * <p>Created by shixin on 2019-04-18.
 */
public class BossViewModel extends ViewModel {
    public final LiveData<Boss> bossLiveData = new LiveData<Boss>(){

    };

    public BossViewModel() {

    }

    void doAction() {
        Boss boss = new Boss();
    }
}
