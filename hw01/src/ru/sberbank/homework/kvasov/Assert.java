package ru.sberbank.homework.kvasov;
public class Assert {

    public static boolean assertOperation(String message, double expected, double actual) {

        double x = Math.round(expected*100.0)/100.0; // округление до сотых ожидаемого числа
        double y = Math.round(actual*100.0)/100.0; // округление до сотых вычесленного числа
        if(y % 1 ==0) {
            System.out.println("Операция: " + message + " = " + (int)y);
        }
        else{
            System.out.println("Операция: " + message + " = " + y);
        }
        if (y == x) {
            System.out.println("true");
            System.out.println();
            return true;
        } else {
            if(x % 1 == 0){
                System.out.println("Ошибка! Ожидалось " + message + " = " + (int)x);
            }
            else{
                System.out.println("Ошибка! Ожидалось " + message + " = " + x);
            }
            System.out.println("false");
            System.out.println();
            return false;
        }

    }
}
