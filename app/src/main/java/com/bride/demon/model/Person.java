package com.bride.demon.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import androidx.annotation.NonNull;

/**
 * <p>Created by shixin on 2019/4/10.
 */
public class Person implements Externalizable {
    private static final long serialVersionUID = 0L;

    private String name;
    private String gender;
    private long createTime;

    public Person() {}

    public Person(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.name);
        out.writeObject(this.gender);
        out.writeLong(this.createTime);
    }

    @Override
    public void readExternal(ObjectInput in) throws ClassNotFoundException, IOException {
        this.name = (String) in.readObject();
        this.gender = (String) in.readObject();
        this.createTime = in.readLong();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getName()+"["+name+", "+gender+"]";
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
