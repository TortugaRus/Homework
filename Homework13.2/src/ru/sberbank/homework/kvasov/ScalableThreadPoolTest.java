package ru.sberbank.homework.kvasov;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ScalableThreadPoolTest {

    private int minSize = 5;
    private int maxSize = 15;
    private ScalableThreadPool threadPool;

    @Before
    public void before() {
        threadPool = new ScalableThreadPool(minSize, maxSize);
    }

    @Test
    public void normalCountTasksTest() throws InterruptedException {
        threadPool.start();
        for (int i = 0; i < 10; i++) {
            threadPool.execute(threadPool.setTask());
        }

        TimeUnit.MILLISECONDS.sleep(100 * 10);

        assertEquals(10, threadPool.getTasksCount());
        assertEquals(minSize, threadPool.getListThreads().size());
        assertEquals(0, threadPool.getQueueSize());
    }


    @Test
    public void overMaxCountTest() throws InterruptedException {
        int countTasks = maxSize + minSize;

        for (int i = 0; i < countTasks; i++) {
            threadPool.execute(threadPool.setTask());
        }
        threadPool.start();

        assertEquals(maxSize, threadPool.getListThreads().size());
        TimeUnit.MILLISECONDS.sleep(100 * countTasks);
        assertEquals(countTasks, threadPool.getTasksCount());
        assertEquals(0, threadPool.getQueueSize());
    }

    @Test
    public void shutdownTest() throws InterruptedException {
        threadPool.start();
        threadPool.shutdown();

        for (int i = 0; i < 10; i++) {
            threadPool.execute(threadPool.setTask());
        }

        TimeUnit.MILLISECONDS.sleep(100 * 10);
        assertEquals(0, threadPool.getTasksCount());
        assertEquals(0, threadPool.getQueueSize());
        assertEquals(minSize, threadPool.getListThreads().size());

    }
}
