package com.bride.demon.model;

import java.io.Serializable;

import androidx.annotation.NonNull;

/**
 * <p>Created by shixin on 2019/4/10.
 */
public class Student implements Serializable {
    private static final long serialVersionUID = 0L;

    private long id;
    private String name;
    private int age;
    private transient String major;

    public Student() {

    }

    public Student(long id, String name, int age, String major) {
        this.setId(id);
        this.setName(name);
        this.setAge(age);
        this.setMajor(major);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getName()+"["+id+", "+name+", "+age+", "+major+"]";
    }
}
