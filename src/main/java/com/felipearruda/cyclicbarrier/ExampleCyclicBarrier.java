package com.felipearruda.cyclicbarrier;

import java.util.concurrent.*;

public class ExampleCyclicBarrier {

    static  CyclicBarrier cyclicBarrier;

    public static void main(String[] args)  {
        int numberOfParticpants = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        cyclicBarrier = new CyclicBarrier(numberOfParticpants, () -> System.out.println("Starting the race in 3,2,1...."));

        Runnable lewisHamilton = () -> {
          preparePilot("Hamilton");
        };

        Runnable maxVerstappen = () -> {
            preparePilot("Max Verstappen");
        };

        Runnable leclerc = () -> {
            preparePilot("Leclerc");
        };

        executorService.submit(lewisHamilton);
        executorService.submit(maxVerstappen);
        executorService.submit(leclerc);

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Forcing shutdown");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    public static void preparePilot(String pilot) {
        System.out.println(String.format("%s is preparing for the race...", pilot));
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            System.err.println(pilot + " was interrupted.");
        }
    }

}
