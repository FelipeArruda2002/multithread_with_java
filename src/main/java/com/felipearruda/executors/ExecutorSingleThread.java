package com.felipearruda.executors;

import java.util.concurrent.*;

public class ExecutorSingleThread {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = null;
        try  {
            // Criar um executor de uma única thread
            executorService = Executors.newSingleThreadExecutor();

            // Mesmo com uma única thread, várias tarefas podem ser enviadas para execução,
            // mas elas serão processadas uma de cada vez, na ordem em que foram submetidas.
            executorService.execute(new MyRunnable());
            executorService.execute(new MyRunnable());
            executorService.execute(new MyRunnable());

            // Submit retorna um objeto Future, que pode ser usado para consultar o status da tarefa ou recuperar o resultado posteriormente.
            Future<String> future =  executorService.submit(new MyCallable());
            //  O método isDone retorna true se a tarefa foi concluída (ou seja, terminou a execução),
            //  ou false se a tarefa ainda está sendo executada
            System.out.println(future.isDone());
            //  O método get() bloqueia a execução até que a tarefa termine, e então retorna o resultado da tarefa.
            //  Se a tarefa já tiver sido concluída, ele simplesmente retorna o valor.
            System.out.println(future.get());
            System.out.println(future.isDone());
            //  O método get() com timeout permite que você defina um tempo máximo de espera pela conclusão da tarefa.
            //  Se a tarefa não terminar dentro do tempo especificado (10 segundos neste caso), uma exceção TimeoutException será lançada.
            System.out.println(future.get(10, TimeUnit.SECONDS));

            // Solicita o desligamento do executor, mas não interrompe as tarefas em execução.
            executorService.shutdown();

            // Aguarda até que todas as tarefas finalizem ou até que o tempo limite de 5 segundos seja atingido
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw e;
        } finally {
            // Garante que todas as tarefas pendentes sejam interrompidas imediatamente.
            // A execução de tarefas ainda ativas é abortada.
            executorService.shutdownNow();
        }

    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("[MyRunnable] - " + Thread.currentThread().getName());
        }
    }

    static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "[MyCallable] - " + Thread.currentThread().getName();
        }
    }

}
