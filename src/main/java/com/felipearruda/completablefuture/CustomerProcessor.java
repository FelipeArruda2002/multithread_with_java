package com.felipearruda.completablefuture;

import java.util.concurrent.CompletableFuture;

public class CustomerProcessor {

    public static void main(String[] args) {
        processCustomer(123).thenAccept(System.out::println).join();
    }

    public static CompletableFuture<String> processCustomer(int customerId) {
        return fetchCustomerData(customerId)
                .thenCompose(customer ->
                        fetchPurchaseHistory(customer)
                                .thenCompose(history ->
                                        calculateLoyaltyScore(history)
                                                .thenApply(score -> "Customer " + customer + "processed with score " + score)
                                )
                );
    }

    private static CompletableFuture<String> fetchCustomerData(int customerId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            System.out.println("Seeking #" + customerId + " client");
            return "João";
        });
    }

    private static CompletableFuture<String> fetchPurchaseHistory(String customerData) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(3000);
            return String.format("Histórico de compras de %s: [TV, Notebook, Celular, Fone de Ouvido]", customerData);
        });
    }

    private static CompletableFuture<Integer> calculateLoyaltyScore(String purchaseHistory) {
        return CompletableFuture.supplyAsync(() -> {
            String items = purchaseHistory.substring(purchaseHistory.indexOf("[") + 1, purchaseHistory.indexOf("]"));
            int amount = items.split(",").length;
            return 20 * amount;
        });
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

