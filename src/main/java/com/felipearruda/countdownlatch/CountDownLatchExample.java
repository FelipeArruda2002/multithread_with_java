package com.felipearruda.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchExample {

    public static void main(String[] args) throws InterruptedException {
        int numberOfTasks = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(numberOfTasks);

        for (int i = 0; i < numberOfTasks; i++) {
            executorService.submit(() -> {
                executeTask();
                latch.countDown();
            });
        }

        latch.await();
        System.out.println("Executing final task...");

        executorService.shutdown();
    }

    public static void executeTask() {
        System.out.println("Executing thread task: " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}