package com.felipearruda.lock.ordersystem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderSystemTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        OrderManager store1 = new OrderManager();
        OrderManager store2 = new OrderManager();

        store1.addOrder(new Order(1, 50.0));
        store1.addOrder(new Order(2, 30.0));
        store2.addOrder(new Order(3, 20.0));

        Runnable readTask = () -> {
            System.out.println(Thread.currentThread().getName() + " - Total Store1: " + store1.getTotalAmount());
            System.out.println(Thread.currentThread().getName() + " - Total Store2: " + store2.getTotalAmount());
        };

        Runnable addTask = () -> store1.addOrder(new Order(4, 40.0));
        Runnable removeTask = () -> store1.removeOrder(2);
        Runnable transferTask = () -> OrderManager.transferOrder(store1, store2, 1);

        executorService.execute(readTask);
        executorService.execute(addTask);
        executorService.execute(removeTask);
        executorService.execute(transferTask);
        executorService.execute(readTask);

        executorService.shutdown();
    }
}
