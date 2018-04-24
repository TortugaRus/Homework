package ru.sberbank.homework.kvasov;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FixedThreadPool implements ThreadPool {

    private LinkedList<Thread> threads;
    private Queue<Runnable> tasks;
    private volatile boolean isPoolWork = true;
    private int tasksCount = 0;

    public FixedThreadPool(int numOfThreads) {
        tasks = new ConcurrentLinkedQueue<>();
        threads = new LinkedList<>();

        for (int i = 0; i < numOfThreads; i++) {
            threads.add(new Thread(new TaskExecutor(isPoolWork, tasks)));
        }
    }

    @Override
    public void start() {
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        if (isPoolWork) {
            tasks.offer(runnable);
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
