package com.bride.client.datastructure;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <p>Created by shixin on 2019-05-13.
 */
public class HorseRace {
    static final int FINISH_LINE = 75;
    private List<Horse> horses = new ArrayList<>();
    private ExecutorService exec = Executors.newCachedThreadPool();
    public HorseRace(int horseNum, int pause) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(horseNum, new Runnable() {
            @Override
            public void run() {
                for (Horse h : horses) {
                    if (h.getStrides() >= FINISH_LINE) {
                        System.out.println(h+" won!");
                        exec.shutdownNow();
                        return;
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(pause);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        for (int i=0; i<horseNum; i++) {
            Horse h = new Horse(cyclicBarrier);
            horses.add(h);
            exec.execute(h);
        }
    }

    public static void main(String[] args) {
        int horseNum = 7;
        int pause = 200;
        new HorseRace(horseNum, pause);
    }
}

class Horse implements Runnable {

    private static int counter = 0;
    private final int id = counter++;
    private int strides = 0;
    private static Random ran = new Random(31);
    private CyclicBarrier cyclicBarrier;

    public synchronized int getStrides() {
        return strides;
    }

    Horse(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    strides += ran.nextInt(3);
                }
                this.cyclicBarrier.await();
            }
        } catch (InterruptedException e) {

        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return Horse.class.getSimpleName() + "[" + id + "]";
    }
}