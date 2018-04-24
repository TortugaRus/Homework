package ru.sberbank.homework.kvasov;

public interface ThreadPool {

    void start();

    void execute(Runnable runnable);
}