package ru.sberbank.homework.kvasov;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathExpressionParsing implements ErrorMessage {

    private String expression;
    private String firstValue, secondValue, operatorValue;
    private String errorMessage;
    private int flagTemplate;

    public MathExpressionParsing(String expression) {
        this.expression = expression;
    }

    public void filterExpressionOnForm() {
        Matcher matcherFirst = Pattern.compile(RegexTemplates.EXPRESSION_TEMPLATE_ONE).matcher(expression);
        Matcher matcherSecond = Pattern.compile(RegexTemplates.EXPRESSION_TEMPLATE_TWO).matcher(expression);

        if (matcherFirst.matches()) {
            String[] arrrayWithExpression = expression.split("\\s");
            firstValue = arrrayWithExpression[0];
            secondValue = arrrayWithExpression[2];
            operatorValue = arrrayWithExpression[1];
            flagTemplate = 1;

        } else {
            if (!matcherSecond.matches()) {
                errorMessage = "error > wrong expression";
                return;
            } else {
                String[] arrrayWithExpression = expression.split("\\s");
                secondValue = arrrayWithExpression[1];
                operatorValue = arrrayWithExpression[0];
                flagTemplate = 2;
            }
        }
    }

    public String getFirstValue() {
        return firstValue;
    }

    public String getSecondValue() {
        return secondValue;
    }

    public String getOperatorValue() {
        return operatorValue;
    }

    public int getFlagTemplate() {
        return flagTemplate;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
