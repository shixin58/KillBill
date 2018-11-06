package com.bride.client.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * <p>Created by shixin on 2018/11/6.
 */
public class ObserverA implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        ObservableCook observableCook = (ObservableCook) o;
        String info = (String) arg;
        System.out.println("ObserverA: "+observableCook.getData()+" "+info);
    }
}
