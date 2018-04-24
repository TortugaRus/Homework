package ru.sberbank.homework.kvasov;

import ru.sberbank.homework.kvasov.ValueAndType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DecimalParser implements ErrorMessage {

    private String value;
    private String currentValue;
    private String lastLiteral;
    private String decimalRegex = RegexTemplates.DECIMAL_REGEX;
    private int dot;
    private String errorMessage;

    public DecimalParser(String value, String lastLiteral) {
        this.value = value;
        currentValue = value.toLowerCase();
        this.lastLiteral = lastLiteral;
    }

    public ValueAndType parseValue() {
        if (isErrorLastLiteral()) {

            return new ValueAndType<>(null);
        }
        if (isLastElementUnderline()) {

            return new ValueAndType<>(null);
        }

        if (!currentValue.matches(decimalRegex)) {
            errorMessage = "error > " + value + lastLiteral;
            return new ValueAndType<>(null);
        }

        if (!isOneDot(currentValue)) {
            return new ValueAndType<>(null);
        }
        if (isLastElementDot()) {
            return new ValueAndType<>(null);
        }
        if (isUnderlineAroundDot()) {
            return new ValueAndType<>(null);
        }

        currentValue = currentValue.replaceAll("_", "");
        return calculateValue(currentValue);
    }

    private ValueAndType calculateValue(String currentValue) {
        try {
            double num = Math.round(Double.parseDouble(currentValue) * 100.0) / 100.0;
            return new ValueAndType<Double>(num);
        } catch (NumberFormatException ex) {
            errorMessage = "error > " + value + lastLiteral;
        }
        return new ValueAndType<>(null);
    }

    private boolean isOneDot(String currentValue) {
        dot = currentValue.indexOf(".");
        int lastDot = currentValue.lastIndexOf(".");
        if (dot != lastDot) {
            errorMessage = "error > " + value + lastLiteral;
            return false;
        }
        return true;
    }

    private boolean isErrorLastLiteral() {
        if (lastLiteral.equals("l")) {
            errorMessage = "error > " + value + lastLiteral;
            return true;
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

    private boolean isLastElementDot() {
        if (currentValue.endsWith(".")) {
            errorMessage = "error > " + value + lastLiteral;
            return true;
        }
        return false;
    }

    private boolean isUnderlineAroundDot() {
        if (currentValue.charAt(dot - 1) == '_' || currentValue.charAt(dot + 1) == '_') {
            errorMessage = "error > " + value + lastLiteral;
            return true;
        }
        return false;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
