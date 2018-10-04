package com.bride.demon;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>Created by shixin on 2018/9/8.
 */
public class Form implements Parcelable {
    private String address;
    private String birth;

    public Form(String address, String birth) {
        this.address = address;
        this.birth = birth;
    }

    protected Form(Parcel in) {
        address = in.readString();
        birth = in.readString();
    }

    public static final Creator<Form> CREATOR = new Creator<Form>() {
        @Override
        public Form createFromParcel(Parcel in) {
            return new Form(in);
        }

        @Override
        public Form[] newArray(int size) {
            return new Form[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel in) {
        this.address = in.readString();
        this.birth = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(birth);
    }

    @Override
    public String toString() {
        return "Form: "+this.address+", "+this.birth;
    }
}
