package com.felipearruda.lock.banksystem;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BankAccount {
    private double balance;
    private final ReadWriteLock readWriteLock;
    private final Lock reentrantLock;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
        this.readWriteLock = new ReentrantReadWriteLock();
        this.reentrantLock = new ReentrantLock();
    }

    public double getBalance() {
        readWriteLock.readLock().lock();
        try {
            Thread.sleep(100);
            return balance;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void deposit(double amount) {
        readWriteLock.writeLock().lock();
        try {
            Thread.sleep(100);
            balance += amount;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void withdraw(double amount) {
        readWriteLock.writeLock().lock();
        try {
            if (balance >= amount) {
                Thread.sleep(100);
                balance -= amount;
            } else {
                System.out.println(Thread.currentThread().getName() + " - Insufficient balance!");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void transfer(BankAccount from, BankAccount to, double amount) {
        BankAccount first = from.hashCode() < to.hashCode() ? from : to;
        BankAccount second = from.hashCode() < to.hashCode() ? to : from;

        first.reentrantLock.lock();
        second.reentrantLock.lock();

        try {
            if (from.getBalance() >= amount) {
                from.withdraw(amount);
                to.deposit(amount);
                System.out.println(Thread.currentThread().getName() + " - transferred R$" + amount);
            } else {
                System.out.println(Thread.currentThread().getName() + " - Transferência falhou (saldo insuficiente)!");
            }
        } finally {
            first.reentrantLock.unlock();
            second.reentrantLock.unlock();
        }
    }

}