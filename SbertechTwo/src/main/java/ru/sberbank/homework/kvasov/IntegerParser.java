package ru.sberbank.homework.kvasov;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegerParser implements ErrorMessage {

    private String value;
    private String currentValue;
    private String firstLiteral;
    private String lastLiteral;
    private String regex;
    private int cursor;
    private int notationValue;
    private String errorMessage;

    public IntegerParser(String value, String lastLiteral) {
        this.value = value;
        currentValue = value.toLowerCase();
        this.lastLiteral = lastLiteral;
        cursor = 0;
    }

    public ValueAndType parseValue() {
        try {
            assignFirstLiteral();
        } catch (StringIndexOutOfBoundsException ex) {
            errorMessage = "error > " + value;
            return new ValueAndType<>(null);
        }

        if (isErrorLastLiteral()) {
            return new ValueAndType<>(null);
        }

        if (isLastElementUnderline()) {
            return new ValueAndType<>(null);
        }

        assignNotationValueAndRegex();

        if (currentValue.charAt(0) == '-') {
            cuteLiteralAndAddNegative();
        } else {
            currentValue = currentValue.substring(cursor, currentValue.length());
            cursor = 0;
        }

        try {
            if (currentValue.charAt(cursor) == '_' && (firstLiteral.equals("0b") || firstLiteral.equals("0x"))) {
                errorMessage = "error > " + value + lastLiteral;
                return new ValueAndType<>(null);
            }
        } catch (StringIndexOutOfBoundsException ex) {
            errorMessage = "error > " + value + lastLiteral;
            return new ValueAndType<>(null);
        }
        currentValue = currentValue.replaceAll("\\_", "");

        if (!currentValue.matches(regex)) {
            errorMessage = "error > " + value + lastLiteral;
            return new ValueAndType<>(null);
        }
        return calculateValue(currentValue, lastLiteral);
    }

    private ValueAndType calculateValue(String currentValue, String lastLiteral) {
        try {
            if (notationValue == 8 || notationValue == 10) {
                if (lastLiteral.equals("d") || lastLiteral.equals("f")) {
                    return new ValueAndType<Double>(Double.parseDouble(currentValue));
                }
            }
            if (lastLiteral.equals("l")) {
                return new ValueAndType<Long>(Long.parseLong(currentValue, notationValue));
            } else {
                return new ValueAndType<Integer>(Integer.parseInt(currentValue, notationValue));
            }
        } catch (NumberFormatException ex) {
            errorMessage = "error > " + value + lastLiteral;
        }
        return new ValueAndType<>(null);
    }

    private void assignNotationValueAndRegex() {
        switch (firstLiteral) {
            case "0x":
                regex = RegexTemplates.HEX_REGEX;
                notationValue = 16;
                cursor += 2;
                break;
            case "0b":
                regex = RegexTemplates.BINARY_REGEX;
                notationValue = 2;
                cursor += 2;
                break;
            case "0":
                regex = RegexTemplates.OCTAL_REGEX;
                notationValue = 8;
                cursor++;
                break;
            default:
                regex = RegexTemplates.INTEGER_REGEX;
                notationValue = 10;
                break;
        }
    }

    private boolean isErrorLastLiteral() {
        if (lastLiteral.equals("d") || lastLiteral.equals("f")) {
            if (firstLiteral.equals("0x") && lastLiteral.equals("f")) {
                return false;
            }
            if (notationValue != 10 && notationValue != 8) {

                errorMessage = "error > " + value + lastLiteral;
                return true;
            }
        }
        return false;
    }

    private boolean isLastElementUnderline() {
        if (currentValue.endsWith("_")) {
            errorMessage = "error > " + value + lastLiteral;
            return true;
        }
        return false;
    }

    private void cuteLiteralAndAddNegative() {
        cursor++;
        currentValue = currentValue.substring(cursor, currentValue.length());
        currentValue = "-" + currentValue;
        cursor = 1;
    }

    private void assignFirstLiteral() throws StringIndexOutOfBoundsException {
        int myCursor = 0;
        if (currentValue.charAt(0) == '-') {
            myCursor++;
        }
        if (currentValue.charAt(myCursor) == '0') { // если
            myCursor++;
            if (currentValue.charAt(myCursor) == 'x') {
                firstLiteral = "0x";
            }
            if (currentValue.charAt(myCursor) == 'b') {
                firstLiteral = "0b";
            }
            if (Character.toString(currentValue.charAt(myCursor)).matches("[0-7\\_]")) {
                firstLiteral = "0";
            }
        } else if (Character.toString(currentValue.charAt(myCursor)).matches("[1-9]")) {
            firstLiteral = "";
        } else {
            errorMessage = "error > " + value;
        }
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
