package com.felipearruda.synchronousqueue;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;

public class OrderProcessingSystem {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        SynchronousQueue<String> queue = new SynchronousQueue<>();

        Thread orderGenerator = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                String order = "Order-" + RANDOM.nextInt(1000);
                System.out.println("Order generated " + order);
                put(queue, order);
                sleep(1000);
            }
        });

        Thread orderProcessor = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                String order = take(queue);
                System.out.println("Processing " + order);
                sleep(2000);
            }
        });

        orderGenerator.start();
        orderProcessor.start();
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void put(SynchronousQueue<String> queue, String order) {
        try {
            queue.put(order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static String take(SynchronousQueue<String> queue) {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}