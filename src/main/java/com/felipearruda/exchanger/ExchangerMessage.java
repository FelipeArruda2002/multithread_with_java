package com.felipearruda.exchanger;

import java.util.concurrent.Exchanger;

public class ExchangerMessage {

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        Thread a = new Thread(() -> {
            String message = "Hello from Thread A";
            System.out.println("[Thread A] Sending message: " + message);
            String received = exchange(exchanger, message);
            System.out.println("[Thread A] Received response: " + received);
        });

        Thread b = new Thread(() -> {
            String message = "Hello from Thread B";
            System.out.println("[Thread B] Sending response: " + message);
            String received = exchange(exchanger, message);
            System.out.println("[Thread B] Received message: " + received);
        });

        a.start();
        b.start();
    }

    private static String exchange(Exchanger<String> exchanger, String message) {
        try {
            return exchanger.exchange(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Exceção";
        }
    }
}
