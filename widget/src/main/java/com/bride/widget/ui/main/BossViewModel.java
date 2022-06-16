package com.bride.widget.ui.main;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bride.baselib.net.Urls;
import com.bride.widget.model.Boss;

/**
 * <p>Created by shixin on 2019-04-18.
 */
public class BossViewModel extends ViewModel {
    // View和ViewModel之间通过LiveData、ObservableField通信
    public final MutableLiveData<Boss> bossLiveData = new MutableLiveData<>();
    public final ObservableField<String> resultImageUrl = new ObservableField<>();

    public BossViewModel() {
    }

    public void doAction(View v) {
        Boss boss = new Boss();
        boss.setName("石鑫");
        boss.setAvatar(Urls.Images.BEAUTY);
        bossLiveData.postValue(boss);
    }

    public void updateScenery(View v) {
        resultImageUrl.set(Urls.Images.LADY);
    }
}
