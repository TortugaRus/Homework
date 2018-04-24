package ru.sberbank.homework.kvasov;

import java.util.Queue;

public class TaskExecutor implements Runnable {
    private final Queue<Runnable> tasks;
    private boolean isWork;

    public TaskExecutor(boolean isWork, Queue tasks) {
        this.isWork = isWork;
        this.tasks = tasks;
    }

    @Override
    public void run() {
        while (isWork && !tasks.isEmpty()) {
            Runnable task = tasks.poll();
            if (task != null) {
                task.run();
            }
        }
    }
}
