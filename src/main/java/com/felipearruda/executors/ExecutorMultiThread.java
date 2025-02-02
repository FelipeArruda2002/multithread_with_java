package com.felipearruda.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorMultiThread {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = null;

        try {
            // Cria um pool de threads fixo com 4 threads para executar tarefas simultaneamente
            executorService = Executors.newFixedThreadPool(4);
            // Cria um pool de threads dinâmico, onde novas threads são criadas conforme necessário e descartadas após 1 minuto de inatividade
            executorService = Executors.newCachedThreadPool();
            List<MyCallable> tasks = new ArrayList<>();
            tasks.add(new MyCallable());
            tasks.add(new MyCallable());
            tasks.add(new MyCallable());
            tasks.add(new MyCallable());

            // Executa todas as tarefas e aguarda a conclusão de todas, retornando uma lista de Future para acessar os resultados
            List<Future<String>> futures = executorService.invokeAll(tasks);
            for (Future<String> future: futures) {
                System.out.println(future.get());
            }

            // Executa todas as tarefas e retorna o resultado da primeira que finalizar (as outras são canceladas)
            String invokeAny = executorService.invokeAny(tasks);
            System.out.println(invokeAny);

            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw e;
        } finally {
            if (executorService != null) {
                executorService.shutdownNow();
            }
        }

    }

    static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "[MyCallable] - " + Thread.currentThread().getName();
        }
    }

}
