package ru.sberbank.homework.kvasov;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class FixedThreadPoolTest {

    private int numOfThreads = 5;
    private int numOfTasks = 15;
    private FixedThreadPool threadPool;

    @Before
    public void setThreadPool() {
        threadPool = new FixedThreadPool(numOfThreads);

    }

    @Test
    public void startTest() throws InterruptedException {

        threadPool.start();

        for (int i = 0; i < numOfTasks; i++) {
            threadPool.execute(threadPool.setTask());
        }

        TimeUnit.MILLISECONDS.sleep(10 * numOfTasks);

        assertEquals(0, threadPool.getQueueSize());
        assertEquals(numOfTasks, threadPool.getTasksCount());
        assertEquals(numOfThreads, threadPool.getListThreads().size());
    }

    @Test
    public void shutdownTest() throws InterruptedException {

        threadPool.start();
        threadPool.shutdown();
        for (int i = 0; i < numOfTasks; i++) {
            threadPool.execute(threadPool.setTask());
        }

        TimeUnit.MILLISECONDS.sleep(10 * numOfTasks);

        assertEquals(0, threadPool.getQueueSize());
        assertEquals(0, threadPool.getTasksCount());
        assertEquals(numOfThreads, threadPool.getListThreads().size());
    }
}
