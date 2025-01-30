package com.felipearruda.synchronizingthreads;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class SyncForCollections {

    // Coleções Thread safe
    static List<String> list = new CopyOnWriteArrayList<>();
    static Map<Integer, String> map = new ConcurrentHashMap<>();
    static Queue<String> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
        MyRunnableForMap myRunnableForMap = new MyRunnableForMap();
        MyRunnableForList myRunnableForList = new MyRunnableForList();
        MyRunnableForQueue myRunnableForQueue = new MyRunnableForQueue();

        Thread t1 = new Thread(myRunnableForMap);
        Thread t2 = new Thread(myRunnableForMap);
        Thread t3 = new Thread(myRunnableForMap);

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(1000);

        System.out.println(map);

        Thread t4 = new Thread(myRunnableForList);
        Thread t5 = new Thread(myRunnableForList);
        Thread t6 = new Thread(myRunnableForList);

        t4.start();
        t5.start();
        t6.start();

        Thread.sleep(1000);

        System.out.println(list);

        Thread t7 = new Thread(myRunnableForQueue);
        Thread t8 = new Thread(myRunnableForQueue);
        Thread t9 = new Thread(myRunnableForQueue);

        t7.start();
        t8.start();
        t9.start();

        Thread.sleep(1000);

        System.out.println(queue);
    }

    public static class MyRunnableForQueue implements Runnable {

        @Override
        public void run() {
            queue.add("Added to the queue");
            System.out.println(Thread.currentThread().getName() + "-> Added to the queue");
        }
    }

    public static class MyRunnableForMap implements Runnable {

        @Override
        public void run() {
            map.put(new Random().nextInt(), "Added to the map");
            System.out.println(Thread.currentThread().getName() + "-> Added to the map");
        }
    }

    public static class MyRunnableForList implements Runnable {

        @Override
        public void run() {
            list.add("Added to the list");
            System.out.println(Thread.currentThread().getName() + "-> Added to the list");
        }
    }
}
