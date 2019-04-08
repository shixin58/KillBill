package com.bride.client.reflect;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created by shixin on 2018/9/1.
 */
public class Human {

    public int performance = 99;

    private String name;

    private int age;

    private double salary;

    private List<String> hobbies = new ArrayList<>();

    public Human() {
    }

    public Human(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
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

    private double getSalary() {
        return salary;
    }

    private void setSalary(double salary) {
        this.salary = salary;
    }


    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
