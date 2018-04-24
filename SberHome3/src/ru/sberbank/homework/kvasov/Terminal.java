package ru.sberbank.homework.kvasov;

public interface Terminal {

    public void checkBankAccountStatus();

    public void withdrawMoney(int money);

    public void depositMoney(int money);

    public boolean pinValidation(String pin);
}
