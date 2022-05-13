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
 * 练习CyclicBarrier用法
 * <p>Created by shixin on 2019-05-13.
 */
public class HorseRace {
    static final int FINISH_LINE = 75;
    private final List<Horse> horses = new ArrayList<>();
    private final ExecutorService exec = Executors.newCachedThreadPool();

    public HorseRace(final int horseNum, final long pauseMillis) {
        // 同步辅助类。cyclic循环的，周期的；trip绊倒，catch your foot on sth and fall
        CyclicBarrier cyclicBarrier = new CyclicBarrier(horseNum, new Runnable() {
            @Override
            public void run() {
                System.out.println("execute barrierAction");
                for (Horse h : horses) {
                    if (h.getStrides() >= FINISH_LINE) {
                        System.out.println(h+" won!");
                        exec.shutdownNow();
                        return;
                    }
                }
                try {
                    // 等价于Thread.sleep(pauseMillis, 0);
                    TimeUnit.MILLISECONDS.sleep(pauseMillis);
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
        long pauseMillis = 200L;
        new HorseRace(horseNum, pauseMillis);
    }
}

class Horse implements Runnable {
    private static int counter = 0;
    private final int id = counter++;

    // stride一大步，one long step
    private int strides = 0;
    private static final Random ran = new Random(31);
    private final CyclicBarrier cyclicBarrier;

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
                    strides += ran.nextInt(3)+1;
                }
                System.out.println(this+" await, strides="+strides);
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