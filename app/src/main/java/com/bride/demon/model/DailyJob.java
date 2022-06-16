package com.bride.demon.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.bride.demon.BR;

public class DailyJob extends BaseObservable {
    private String name;
    private int priority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
        notifyPropertyChanged(BR.priority);
    }
}
