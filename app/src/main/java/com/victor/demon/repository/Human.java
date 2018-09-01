package com.victor.demon.repository;

/**
 * <p>Created by shixin on 2018/9/1.
 */
public class Human {

    public int performance = 99;

    private String name;

    private int age;

    private double salary;

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
}
