package com.felipearruda.semaphore;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class BathroomSimulation {

    private static int counter = 0;

    public static void main(String[] args) {
        Semaphore publicRestroom = new Semaphore(2);

        ExecutorService executorService = Executors.newCachedThreadPool();

        Runnable bathroomUserTask = () -> {
            int idUsuario = new Random().nextInt(1000);

            acquire(publicRestroom);
            System.out.println(String.format("User %d entered the bathroom", getUniqueUserId()));
            sleep();
            System.out.println(String.format("User %d left the bathroom", getUniqueUserId()));
            publicRestroom.release();
        };

        for (int i = 0; i < 100; i++) {
            executorService.execute(bathroomUserTask);
        }

        executorService.shutdown();
    }

    private static void acquire(Semaphore publicRestroom) {
        try {
            publicRestroom.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sleep() {
        try {
            Thread.sleep((new Random().nextInt(5) + 1) * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static synchronized int getUniqueUserId() {
        return counter++;  // Incremente o contador e retorne o valor
    }
}
