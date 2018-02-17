package ru.sberbank.homework.kvasov;

public class SimpleCalculatorTest {

    public SimpleCalculatorTest(){
        SimpleCalculator simp = new SimpleCalculator();

        //Тесты сложения
        Assert.assertOperation("2 + 7", 9, simp.sum(2, 7));
        Assert.assertOperation("0 + 2", 2, simp.sum(0, 2));
        Assert.assertOperation("6.4 + 7", 13.4, simp.sum(6.4, 7));
        Assert.assertOperation("1 + 5.0", 6, simp.sum(1, 5));
        Assert.assertOperation("0.1 + 2", 2.1, simp.sum(0.1, 2));

        //тесты вычитания
        Assert.assertOperation("4.3 - 6", -1.7, simp.subtraction(4.3, 6));
        Assert.assertOperation("0 - 1.5", -1.5, simp.subtraction(0, 1.5));
        Assert.assertOperation("3.4 - 1.876", 1.52, simp.subtraction(3.4, 1.876));
        Assert.assertOperation("0.9 - 1", -0.1, simp.subtraction(0.9, 1));
        Assert.assertOperation("339.576 - 677.01", -337.43, simp.subtraction(339.576, 677.01));

        //Тесты умножения
        Assert.assertOperation("4.3 * 6", 25.8, simp.multiplication(4.3, 6));
        Assert.assertOperation("0 * 6.1", 0, simp.multiplication(0, 6.1));
        Assert.assertOperation("5 * 3", 15, simp.multiplication(5, 3));
        Assert.assertOperation("0.7 * 99", 69.3, simp.multiplication(0.7, 99));
        Assert.assertOperation("12.43 * 85.3", 1060.279, simp.multiplication(12.43, 85.3));

        //Тесты деления
        Assert.assertOperation("10 : 5", 2, simp.devision(10, 5));
        Assert.assertOperation("0 : 0.1", 0, simp.devision(0, 0.1));
        Assert.assertOperation("13.1 : 0", 2, simp.devision(13.1, 0));


    }
}
