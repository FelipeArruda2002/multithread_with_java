package com.felipearruda.lock.ordersystem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class OrderManager {
    private final List<Order> orders = new ArrayList<>();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock transferLock = new ReentrantLock();

    public void addOrder(Order order) {
        readWriteLock.writeLock().lock();
        try {
            orders.add(order);
            System.out.println(Thread.currentThread().getName() + " - Order " + order.getId() + " added.");
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void removeOrder(int orderId) {
        readWriteLock.writeLock().lock();
        try {
            orders.removeIf(order -> order.getId() == orderId);
            System.out.println(Thread.currentThread().getName() + " - Order " + orderId + " removed.");
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public List<Order> getOrders() {
        readWriteLock.readLock().lock();
        try {
            return new ArrayList<>(orders);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public double getTotalAmount() {
        readWriteLock.readLock().lock();
        try {
            return orders.stream().mapToDouble(Order::getAmount).sum();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public static void transferOrder(OrderManager from, OrderManager to, int orderId) {
        OrderManager orderManager1 = from.hashCode() < to.hashCode() ? from : to;
        OrderManager orderManager2 = from.hashCode() < to.hashCode() ? to : from;

        orderManager1.transferLock.lock();
        orderManager2.transferLock.lock();

        try {
            // Buscar o pedido com segurança (evitando acessar a lista diretamente)
            Order orderToTransfer = null;
            for (Order order : from.getOrders()) {
                if (order.getId() == orderId) {
                    orderToTransfer = order;
                    break;
                }
            }

            if (orderToTransfer != null) {
                from.removeOrder(orderId);
                to.addOrder(orderToTransfer);
                System.out.println(Thread.currentThread().getName() + " - Order " + orderId + " transfered.");
            } else {
                System.out.println(Thread.currentThread().getName() + " - Order " + orderId + " not found.");
            }
        } finally {
            orderManager2.transferLock.unlock();
            orderManager1.transferLock.unlock();
        }
    }


}