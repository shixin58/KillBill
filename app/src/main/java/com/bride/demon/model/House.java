package com.bride.demon.model;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import androidx.annotation.NonNull;

import timber.log.Timber;

/**
 * <p>Created by shixin on 2019/4/10.
 */
public class House implements Serializable {
    private static final long serialVersionUID = 0L;

    private String address;
    private float space;
    private int floor;

    // 创建新对象分两部：1、创建空对象(new)；2、调用某个构造器(invokespecial "<init>")。
    // 反序列化，不调用当前类构造器。从继承链搜索第一个非序列化的类，调用其无参构造。
    // sun.misc.Unsafe#allocateInstance(House.class)创建新对象不调用构造器。
    private House() {
        Timber.i("new House()");
    }

    public House(String address, float space, int floor) {
        this.address = address;
        this.space = space;
        this.floor = floor;
    }

    // 若有，通过反射调用；否则调用系统默认
    private void writeObject(@NotNull ObjectOutputStream out) throws IOException {
        Timber.i("writeObject");
        out.writeObject(this.address);
        out.writeFloat(this.space);
    }

    private void readObject(@NotNull ObjectInputStream in) throws IOException, ClassNotFoundException {
        Timber.i("readObject");
        this.address = (String) in.readObject();
        this.space = in.readFloat();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getSpace() {
        return space;
    }

    public void setSpace(float space) {
        this.space = space;
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getName()+"["+address+", "+space+", "+floor+"]";
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
