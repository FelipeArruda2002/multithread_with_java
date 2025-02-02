package com.felipearruda.executors;

import java.util.concurrent.*;

public class ExecutorScheduled {

    public static void main(String[] args) {
        // Cria um executor agendado com 3 threads para execução de tarefas periódicas ou com atraso
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);

        // Executa a tarefa uma única vez após um atraso de 10 segundos
        ScheduledFuture<?> schedule = executorService.schedule(new MyRunnable(), 10, TimeUnit.SECONDS);

        // Executa a tarefa com um atraso inicial de 0 segundos e depois repete a execução a cada 5 segundos, fixando o intervalo entre as execuções
        executorService.scheduleAtFixedRate(new MyRunnable(), 0, 5, TimeUnit.SECONDS);

        // Executa a tarefa com um atraso inicial de 0 segundos e repete a execução com um atraso de 1 segundo após a conclusão de cada execução
        executorService.scheduleWithFixedDelay(new MyRunnable(), 0, 1, TimeUnit.SECONDS);
    }

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("[MyRunnable] - " + Thread.currentThread().getName());
        }
    }
}
