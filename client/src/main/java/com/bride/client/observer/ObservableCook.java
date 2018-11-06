package com.bride.client.observer;

import java.util.Observable;

/**
 * <p>Created by shixin on 2018/11/6.
 */
public class ObservableCook extends Observable {
    private int data = 0;

    public void setData(int data) {
        this.data = data;
        this.setChanged();
        this.notifyObservers("OK");
    }

    public int getData() {
        return data;
    }

    public static void main(String[] args) {
        ObservableCook observableCook = new ObservableCook();
        ObserverA observerA = new ObserverA();
        observableCook.addObserver(observerA);
        ObserverB observerB = new ObserverB();
        observableCook.addObserver(observerB);

        observableCook.setData(1);
    }
}
