package com.bride.demon.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * <p>Created by shixin on 2019/4/10.
 */
public class City implements Parcelable {

    private String name;
    private String level;
    private long createTime;

    public City(String name, String level) {
        this.name = name;
        this.level = level;
    }

    public City(Parcel in) {
        this.name = in.readString();
        this.level = in.readString();
        this.createTime = in.readLong();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.level);
        dest.writeLong(this.createTime);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getName()+"["+name+", "+level+", "+createTime+"]";
    }
}
