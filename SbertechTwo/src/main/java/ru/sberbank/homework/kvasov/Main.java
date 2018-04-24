package ru.sberbank.homework.kvasov;

import java.util.*;

import ru.sberbank.homework.kvasov.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression;
        MathCalculator mathCalculator = new MathCalculator();
        while (true) {
            expression = scanner.nextLine();
            if (expression.equals("quit")) {
                scanner.close();
                return;
            }
            System.out.println(mathCalculator.calculate(expression));
        }
    }

}

