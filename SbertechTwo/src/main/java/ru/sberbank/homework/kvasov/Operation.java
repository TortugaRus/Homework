package ru.sberbank.homework.kvasov;

public enum Operation {
    ADD("+"),
    SUB("-"),
    MULT("*"),
    DEV("/");

    private String operator;

    Operation(String operator) {
        this.operator = operator;
    }

}
