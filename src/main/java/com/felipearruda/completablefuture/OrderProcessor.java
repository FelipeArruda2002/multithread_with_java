package com.felipearruda.completablefuture;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class OrderProcessor {

    public static void main(String[] args) {
        processOrder(456).thenAccept(System.out::println).join();
    }

    public static CompletableFuture<String> processOrder(int orderId) {
        return fetchOrderData(orderId)
                .thenCompose(orderInfo -> fetchOrderItems(orderInfo)
                        .thenCompose(items -> calculateTotal(items)
                                .thenCompose(total -> applyDiscount(total)
                                        .thenApply(finalTotal ->
                                                String.format("Order %d processed with final total $%.2f", orderId, finalTotal)
                                        )
                                )
                        )
                );
    }

    private static CompletableFuture<String> fetchOrderData(int orderId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return String.format("Order # %d - Customer Felipe Arruda", orderId);
        });
    }

    private static CompletableFuture<String> fetchOrderItems(String orderData) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(3000);
            return "Order items: [Laptop - $1000.0, Mouse - $50.0, Keyboard - $100.0]";
        });
    }

    private static CompletableFuture<Double> calculateTotal(String orderItems) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            String items = getItems(orderItems);
            return getPrice(items);
        });
    }

    private static CompletableFuture<Double> applyDiscount(double totalAmount) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return totalAmount * 0.9;
        });
    }

    private static Double getPrice(String items) {
        return Arrays.stream(items.split(","))
                .map(i -> i.trim().replaceAll(".*\\$([0-9.]+)", "$1"))  // Captura apenas o preço após $
                .map(Double::parseDouble)
                .reduce(0.00, Double::sum);
    }

    private static String getItems(String orderItems) {
        int start = orderItems.indexOf("[") + 1;
        int end = orderItems.lastIndexOf("]");

        if (start <= 0 || end <= start) return "";  // Evita exceções caso a String esteja mal formatada

        return orderItems.substring(start, end);
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
