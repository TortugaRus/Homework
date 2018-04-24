package ru.sberbank.homework.kvasov;

import ru.sberbank.homework.common.Calculator;
import ru.sberbank.homework.kvasov.MathExpressionParsing;
import ru.sberbank.homework.kvasov.ValueAndType;

public class MathCalculator implements Calculator, ErrorMessage {

    private MathExpressionParsing mathParser;
    private String firstParsValue;
    private String secondParsValue;
    private String operator;
    private ValueAndType firstValue;
    private ValueAndType secondValue;
    private Number firstResultValue;
    private Number secondResultValue;
    private String result;
    private String previousResult;
    private int flagTemplateExpression;
    private String errorMessage;


    @Override
    public String calculate(String userInput) {
        mathParser = new MathExpressionParsing(userInput);
        mathParser.filterExpressionOnForm();
        flagTemplateExpression = mathParser.getFlagTemplate();

        if (mathParser.getErrorMessage() != null) {
            previousResult = null;
            return mathParser.getErrorMessage();
        }
        if (flagTemplateExpression == 2) {

            if (previousResult == null) {
                return "error > wrong expression";
            }

            firstParsValue = previousResult;
        } else {
            firstParsValue = mathParser.getFirstValue();
        }
        operator = mathParser.getOperatorValue();
        secondParsValue = mathParser.getSecondValue();

        NumberParser firstNumberParser = new NumberParser(firstParsValue);
        firstValue = firstNumberParser.getResult();
        if (firstNumberParser.getErrorMessage() != null) {
            previousResult = null;
            return firstNumberParser.getErrorMessage();
        }
        NumberParser secondNumberParser = new NumberParser(secondParsValue);
        secondValue = secondNumberParser.getResult();
        if (secondNumberParser.getErrorMessage() != null) {
            previousResult = null;
            return secondNumberParser.getErrorMessage();
        }

        firstResultValue = firstValue.getValue();
        secondResultValue = secondValue.getValue();

        double decimalResult = 0.0;
        decimalResult = getMathResult(firstResultValue.doubleValue(), secondResultValue.doubleValue(), operator);

        if (Double.isInfinite(decimalResult)) {
            previousResult = null;
            return "error > деление на ноль";
        }
        return formatResult(decimalResult);

    }

    private double sum(double firstValue, double secondValue) {
        return firstValue + secondValue;
    }

    private double sub(double firstValue, double secondValue) {
        return firstValue - secondValue;
    }

    private double mult(double firstValue, double secondValue) {
        return firstValue * secondValue;
    }

    private double devide(double firstValue, double secondValue) {
        return firstValue / secondValue;
    }

    private boolean operatorIsNull(String operator) {
        return (operator == null);
    }

    private double getMathResult(Number first, Number second, String operator) {
        switch (operator) {
            case "+":
                return sum(firstResultValue.doubleValue(), secondResultValue.doubleValue());
            case "-":
                return sub(firstResultValue.doubleValue(), secondResultValue.doubleValue());
            case "*":
                return mult(firstResultValue.doubleValue(), secondResultValue.doubleValue());
            case "/":
                return devide(firstResultValue.doubleValue(), secondResultValue.doubleValue());
            default:
                throw new IllegalArgumentException();
        }
    }

    private String formatResult(double decimalResult) {
        String result = String.format("%.2f", decimalResult);
        result = result.replaceAll(",", ".");
        String arrayWithResult[] = result.split("\\.");
        String decimalPart = arrayWithResult[1];
        if (decimalPart.equals("00")) {
            previousResult = arrayWithResult[0];
            return arrayWithResult[0];
        }
        if (result.endsWith("0")) {
            result = result.substring(0, result.length() - 1);
        }
        previousResult = result;
        return result;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
