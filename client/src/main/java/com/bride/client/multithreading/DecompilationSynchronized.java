package com.bride.client.multithreading;

// 反编译字节码查看synchronized加锁和释放锁的机制。monitorenter和monitorexit指令
// 右击源文件，copy Absolute Path；
// cd path, javac file, javap -verbose *.class
public class DecompilationSynchronized {
    private final Object lock = new Object();

    public void execSynchronized() {
        synchronized (lock) {
            System.out.println("enter synchronized block");
        }
    }
}
