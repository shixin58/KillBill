package com.bride.demon.model;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.bride.demon.BR;

/**
 * 测试DataBinding双向绑定
 */
public class DailyJob extends BaseObservable {
    private String name;
    private int priority;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!TextUtils.equals(this.name, name)) {
            this.name = name;
            notifyPropertyChanged(BR.name);
        }
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
