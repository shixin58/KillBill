package com.bride.widget.ui.main;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bride.baselib.net.Urls;
import com.bride.widget.model.Boss;

/**
 * <p>Created by shixin on 2019-04-18.
 */
public class BossViewModel extends ViewModel {
    public final MutableLiveData<Boss> bossLiveData = new MutableLiveData<>();
    public final ObservableField<String> resultImageUrl = new ObservableField<>();

    public BossViewModel() {

    }

    public void doAction() {
        Boss boss = new Boss();
        boss.setName("石鑫");
        boss.setAvatar(Urls.Images.BEAUTY);
        bossLiveData.postValue(boss);
    }

    public void updateScenery() {
        resultImageUrl.set(Urls.Images.LADY);
    }
}
