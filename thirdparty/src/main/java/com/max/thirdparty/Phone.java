package com.max.thirdparty;

/**
 * <p>Created by shixin on 2018/9/10.
 */
public class Phone {

    public static void main(String[] args) {
        System.out.println("Phone.main");
    }

    private String screen;

    public Phone(String screen) {
        this.screen = screen;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }
}
