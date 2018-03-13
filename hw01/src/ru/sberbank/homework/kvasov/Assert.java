package ru.sberbank.homework.kvasov;
public class Assert {

    public static void assertOperation(String message, double expected, double actual) {
        double x = Math.round(expected * 100.0) / 100.0;
        double y = Math.round(actual * 100.0) / 100.0;
        if (x != y) {
            throw new MathException(message + " fail");
        }
    }
}
