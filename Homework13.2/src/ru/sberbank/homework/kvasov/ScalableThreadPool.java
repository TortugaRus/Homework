package ru.sberbank.homework.kvasov;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ScalableThreadPool implements ThreadPool {

    private Queue<Runnable> tasks;
    private LinkedList<Thread> threads;
    private volatile boolean isPoolWork = true;
    private volatile int tasksCount = 0;
    private int minCountThreads;
    private int maxCountThreads;

    public ScalableThreadPool(int minCountThreads, int maxCountThreads) {
        tasks = new ConcurrentLinkedQueue<>();
        threads = new LinkedList<>();
        this.minCountThreads = minCountThreads;
        this.maxCountThreads = maxCountThreads;

        for (int i = 0; i < minCountThreads; i++) {
            threads.add(new Thread(new TaskExecutor(isPoolWork, tasks)));
        }
    }

    @Override
    public void start() {
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).start();
        }
        new Thread(new ScalablePoolEqualize(isPoolWork, threads, tasks, minCountThreads)).start();
    }

    @Override
    public void execute(Runnable runnable) {
        if (isPoolWork) {
            tasks.offer(runnable);
            if (tasks.size() <= maxCountThreads && tasks.size() > minCountThreads) {
                threads.add(new Thread(new TaskExecutor(isPoolWork, tasks)));
            }
        }
    }

    public Runnable setTask() {
        return () -> tasksCount++;
    }

    public List<Thread> getListThreads() {
        return threads;
    }

    public int getTasksCount() {
        return tasksCount;
    }

    public int getQueueSize() {
        return tasks.size();
    }

    public void shutdown() {
        isPoolWork = false;
    }
}
