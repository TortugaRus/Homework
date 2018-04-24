package ru.sberbank.homework.kvasov;

import ru.sberbank.homework.kvasov.ValueAndType;
import sun.awt.SunHints;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberParser implements ErrorMessage {

    private String value;
    private String currentValue;
    private String errorMessage;

    public NumberParser(String value) {
        this.value = value;
        currentValue = value.toLowerCase();

    }

    public ValueAndType getResult() {
        String lastLiteral;
        int cursor = 0;
        if (currentValue.charAt(0) == '+') {
            currentValue = currentValue.substring(1, currentValue.length());
        }
        lastLiteral = setLastLiteral(currentValue.charAt(currentValue.length() - 1));
        if (!lastLiteral.equals("")) {
            currentValue = currentValue.substring(0, currentValue.length() - 1);
        }
        if (isNegativeValue()) {
            cursor++;
        }
        if (currentValue.charAt(cursor) == '0') {
            cursor++;
            return checkSecondElementAndGetResult(lastLiteral, cursor);
        } else {
            return checkFirstElementAndGetResult(lastLiteral, cursor);
        }
    }

    private String setLastLiteral(char lastLiteral) {
        String resultLiteral;
        if (lastLiteral == 'f' || lastLiteral == 'l' || lastLiteral == 'd') {
            resultLiteral = Character.toString(lastLiteral);
            return resultLiteral;
        } else {
            return "";
        }
    }

    private boolean isNegativeValue() {
        return currentValue.charAt(0) == '-';
    }

    private ValueAndType checkSecondElementAndGetResult(String lastLiteral, int cursor) {
        String secondElement;
        try {
            secondElement = Character.toString(currentValue.charAt(cursor));

        } catch (StringIndexOutOfBoundsException ex) {

            return new ValueAndType<Integer>(0);
        }
        if (currentValue.contains(".")) {
            return getDecimalValue(currentValue, lastLiteral);
        } else if (secondElement.matches("[0-7xb\\_]")) {
            if (lastLiteral.equals("f")) {
                currentValue = currentValue.concat("f");
                lastLiteral = "";
            }
            return getIntegerValue(currentValue, lastLiteral);
        } else {
            errorMessage = "error > " + value;
            return new ValueAndType<>(null);
        }
    }

    private ValueAndType checkFirstElementAndGetResult(String lastLiteral, int cursor) {
        if (currentValue.contains(".")) {
            return getDecimalValue(currentValue, lastLiteral);
        } else if (Character.toString(currentValue.charAt(cursor)).matches("[0-9]")) {
            return getIntegerValue(currentValue, lastLiteral);
        } else {
            errorMessage = "error > " + value;
            return new ValueAndType<>(null);
        }
    }

    private ValueAndType getIntegerValue(String currentValue, String lastLiteral) {
        IntegerParser intParser = new IntegerParser(currentValue, lastLiteral);
        ValueAndType intResult = intParser.parseValue();
        errorMessage = intParser.getErrorMessage();
        return intResult;
    }

    private ValueAndType getDecimalValue(String currentValue, String lastLiteral) {
        DecimalParser decimalParser = new DecimalParser(currentValue, lastLiteral);
        ValueAndType decimalResult = decimalParser.parseValue();
        errorMessage = decimalParser.getErrorMessage();
        return decimalResult;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
