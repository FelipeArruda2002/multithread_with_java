package com.felipearruda.lock.banksystem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BankSystem {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        BankAccount account1 = new BankAccount(1000);
        BankAccount account2 = new BankAccount(1000);

        Runnable readBalance = () -> {
            System.out.println(Thread.currentThread().getName() + " - Account balance 1: " + account1.getBalance());
            System.out.println(Thread.currentThread().getName() + " - Account balance 2: " + account2.getBalance());
        };

        Runnable depositTask = () -> account1.deposit(200);
        Runnable withdrawTask = () -> account1.withdraw(150);
        Runnable transferTask = () -> BankAccount.transfer(account1, account2, 300);

        executorService.execute(readBalance);
        executorService.execute(depositTask);
        executorService.execute(withdrawTask);
        executorService.execute(transferTask);
        executorService.execute(readBalance);

        executorService.shutdown();
    }
}