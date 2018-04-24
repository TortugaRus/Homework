package ru.sberbank.homework.kvasov;

import java.util.LinkedList;
import java.util.Queue;

public class ScalablePoolEqualize implements Runnable {

    private boolean isPoolWork;
    private LinkedList<Thread> threads;
    private Queue<Runnable> tasks;
    private int minCountThreads;

    public ScalablePoolEqualize(boolean isPoolWork, LinkedList<Thread> threads, Queue<Runnable> tasks, int minCount) {
        this.isPoolWork = isPoolWork;
        this.threads = threads;
        this.tasks = tasks;
        this.minCountThreads = minCount;

    }

    @Override
    public void run() {
        while (isPoolWork) {
            while (tasks.size() < threads.size() && threads.size() > minCountThreads) {
                threads.getLast().interrupt();
                threads.remove(threads.size() - 1);
            }
        }
    }
}
