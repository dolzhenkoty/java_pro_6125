package org.javapro.threads.threadpool;

public class Main {
    public static void main(String[] args) {
        final int threadsCount = 4;

        CustomThreadPool pool = new CustomThreadPool(threadsCount);

        for (int i = 0; i < 30; i++) {
            final int taskId = i;
            pool.execute(() -> {
                System.out.println("Task " + taskId + " is running on thread " + Thread.currentThread().getName());
                try {
                    int wTime = 200 + (int)(5000 * Math.random());
                    System.out.println("Thread " + Thread.currentThread().getName() + " will finish task " + taskId + " in " + wTime + " msecs");
                    Thread.sleep(wTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task " + taskId + " is completed on thread " + Thread.currentThread().getName());
            });
        }

        pool.shutdown();

        try {
            pool.awaitTermination();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("All tasks are completed and the thread pool is shut down.");

    }
}