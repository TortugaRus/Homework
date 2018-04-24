package ru.sberbank.homework.kvasov;

public class ValueAndType<T extends Number> {

    private T value;

    public ValueAndType(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
