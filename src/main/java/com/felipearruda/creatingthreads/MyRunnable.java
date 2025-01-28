package com.felipearruda.creatingthreads;

public class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("[MyRunnable] - " + Thread.currentThread().getName());
    }
}
