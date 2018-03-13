package ru.sberbank.homework.kvasov;

public class SimpleCalculator {

    public double sum(double first, double second) {
        double x = first + second;
        if (x % 1 == 0) {
            return (int) x;
        }
        return x;
    }

    public double subtraction(double first, double second) {
        double x = first - second;
        if (x % 1 == 0) {
            return (int) x;
        }
        return x;
    }

    public double multiplication(double first, double second) {
        double x = first * second;
        if (x % 1 == 0) {
            return (int) x;
        }
        return x;

    }

    public double devide(double first, double second) {
        if (second == 0) {
            throw new MathException("Деление на ноль!");
        }
        double x = first / second;
        if (x % 1 == 0) {
            return (int) x;
        }
        return x;
    }
}

