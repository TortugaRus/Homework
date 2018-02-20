package ru.sberbank.homework.kvasov;

public class SimpleCalculator {

    //сумма
    public double sum(double first,double second){

        double x = first + second;
        if(x%1 ==0){ // условие необходимо для наглядного представления int числа
            return (int)x;
        }
        return x;
    }
    //вычитание
    public double subtraction(double first,double second){

        double x = first - second;

        if(x%1 ==0){
            return (int)x;
        }
        return x;
    }
    //умножение
    public double multiplication(double first,double second){

        double x = first * second;

        if(x%1 ==0){
            return (int)x;
        }
        return x;

    }
    //деление
    public double devision(double first,double second){

        if(second ==0){
            throw new MyException("Деление на ноль!");
        }
        double x = first / second;
        if(x%1 ==0){
            return (int)x;
        }
        return x;
    }
}
