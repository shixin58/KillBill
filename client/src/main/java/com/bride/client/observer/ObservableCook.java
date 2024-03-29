package com.bride.client.observer;

import java.util.Observable;

/**
 * 观察者模式 demo
 * <p>Created by shixin on 2018/11/6.
 */
public class ObservableCook extends Observable {
    private int data = 0;

    public void setData(int data) {
        this.data = data;
        // setChanged()/hasChanged()/clearChanged()
        this.setChanged();
        this.notifyObservers("OK");
    }

    public int getData() {
        return data;
    }

    public static void main(String[] args) {
        ObservableCook observableCook = new ObservableCook();
        ObserverA observerA = new ObserverA();
        // Vector<Observer>#addElement()
        // deleteObserver() -> removeElement()
        observableCook.addObserver(observerA);
        ObserverB observerB = new ObserverB();
        observableCook.addObserver(observerB);

        observableCook.setData(1);
    }
}
