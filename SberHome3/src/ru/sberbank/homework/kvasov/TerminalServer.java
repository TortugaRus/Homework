package ru.sberbank.homework.kvasov;

public class TerminalServer {

    private String pin = "7869";
    private int balance = 1000;

    public int viewBalance(PinValidator validator) {
        if (validator.validate()) {
            return balance;
        } else {
            throw new TerminalServerException();
        }
    }

    public void putMoney(PinValidator validator, int money) {
        if (validator.validate()) {
            balance += money;
        } else {
            throw new TerminalServerException();
        }
    }

    public void getMoney(PinValidator validator, int money) {
        if (validator.validate()) {
            if (!(balance < money)) {
                balance -= money;
            } else {
                throw new NotEnoughMoneyException();
            }
        } else {
            throw new TerminalServerException();
        }
    }

    public PinValidator getPinValidator() {
        return new PinValidator(pin);
    }
}
