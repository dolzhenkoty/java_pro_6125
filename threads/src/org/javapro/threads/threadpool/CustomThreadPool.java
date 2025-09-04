package org.javapro.threads.threadpool;

import java.util.LinkedList;
import java.util.List;

public class CustomThreadPool {
    private final int capacity;
    private final List<WorkerThread> workers;
    private final LinkedList<Runnable> taskQueue;
    private volatile boolean isShutdown = false;

    public CustomThreadPool(int threadsCount) {
        this.capacity = threadsCount;
        this.workers = new LinkedList<>();
        this.taskQueue = new LinkedList<>();

        for (int i = 0; i < capacity; i++) {
            WorkerThread worker = new WorkerThread();
            workers.add(worker);
            worker.start();
        }
    }

    public void execute(Runnable task) {
        if (isShutdown) {
            throw new IllegalStateException("ThreadPool is shutting down");
        }

        synchronized (taskQueue) {
            taskQueue.addLast(task);
        }
    }


    public void shutdown() {
        isShutdown = true;
    }

    public void awaitTermination() throws InterruptedException {
        for (WorkerThread worker : workers) {
            worker.join();
        }
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (!isShutdown || !taskQueue.isEmpty()) {
                Runnable task = null;
                synchronized (taskQueue) {
                    if (!taskQueue.isEmpty()) {
                        task = taskQueue.removeFirst();
                    }
                }
                if (task != null) task.run();
            }
        }
    }
}
