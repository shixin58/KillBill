package com.bride.demon;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>Created by shixin on 2018/9/8.
 */
public class User implements Parcelable {
    private String name;
    private String id;
    private String pId;

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeString(this.pId);
    }

    public void readFromParcel(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
        this.pId = in.readString();
    }

    public User(String name, String id, String pId) {
        this.name = name;
        this.id = id;
        this.pId = pId;
    }

    public User(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
        this.pId = in.readString();
    }

    @Override
    public String toString() {
        return "User: "+this.name+", "+this.id+", "+this.pId;
    }
}
