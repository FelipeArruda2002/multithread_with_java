package com.felipearruda.lock.inventory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class InventoryManager {
    private int stock;
    ReadWriteLock readWriteLock;

    public InventoryManager(int initialStock) {
        this.stock = initialStock;
        this.readWriteLock = new ReentrantReadWriteLock();
    }

    public int getStock() {
        // Simular tempo de leitura
        Lock readLock = readWriteLock.readLock();
        readLock.lock();
        try {
            Thread.sleep(100);
            return stock;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return -1;
        } finally {
            readLock.unlock();
        }
    }

    public void addStock(int amount) {
        // Simular tempo de escrita
        Lock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        stock += amount;
        writeLock.unlock();
    }

    public void removeStock(int amount) {
        // Simular tempo de escrita
        Lock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (stock >= amount) {
            stock -= amount;
        } else {
            System.out.println("insufficient stock!");
        }
        writeLock.unlock();
    }
}

