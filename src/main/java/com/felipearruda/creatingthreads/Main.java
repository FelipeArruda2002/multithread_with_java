package com.felipearruda.creatingthreads;

public class Main {

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();

        Thread t1 = new Thread(myRunnable);

        // Thread com lambda
        Thread t2 = new Thread(() -> System.out.println("[Lambda] - " + Thread.currentThread().getName()));

        Thread t3 = new Thread(myRunnable);

        // Run executa a tarefa na mesma thread
        t1.run();
        // Start cria uma nova thread e executa a tarefa
        t1.start();
        t2.start();
        t3.start();
    }
    
}