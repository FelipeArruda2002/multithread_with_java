package com.felipearruda.lock.inventory;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class InventoryTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        InventoryManager inventory = new InventoryManager(10);

        Runnable readTask = () -> {
            System.out.println(Thread.currentThread().getName() + " - Stock: " + inventory.getStock());
        };

        Runnable writeTask = () -> {
            inventory.addStock(5);
            System.out.println(Thread.currentThread().getName() + " - Added stock.");
        };

        executorService.execute(readTask);
        executorService.execute(readTask);
        executorService.execute(writeTask);
        executorService.execute(readTask);
        executorService.execute(() -> inventory.removeStock(3));
        executorService.execute(readTask);

        executorService.shutdown();
    }
}
