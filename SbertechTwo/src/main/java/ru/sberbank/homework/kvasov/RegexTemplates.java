package ru.sberbank.homework.kvasov;

public class RegexTemplates {

    public static final String EXPRESSION_TEMPLATE_ONE = "^([\\-\\+])?([a-zA-Z0-9_\\.\\_])+[\\s]" +
            "[*/+-][\\s]([\\-\\+])?([a-zA-Z0-9_\\.\\_])+$";
    public static final String EXPRESSION_TEMPLATE_TWO = "^[*/+-][\\s]([\\-\\+])?([a-zA-Z0-9_\\.\\_])+$";
    public static final String HEX_REGEX = "([\\-])?([0-9abcdef])+";
    public static final String BINARY_REGEX = "([\\-])?([01])+";
    public static final String OCTAL_REGEX = "([\\-])?([0-7])+";
    public static final String INTEGER_REGEX = "([\\-])?([0-9])+";
    public static final String DECIMAL_REGEX = "[-]?([0-9\\.\\_])+";
}
