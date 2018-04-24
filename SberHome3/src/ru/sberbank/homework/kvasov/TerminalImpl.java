package ru.sberbank.homework.kvasov;

public class TerminalImpl implements Terminal{

    private final TerminalServer server = new TerminalServer();
    private final PinValidator pinValidator = server.getPinValidator();

    @Override
    public boolean pinValidation(String pin) {
        pinValidator.checkValidate(pin);
        return pinValidator.validate();
    }

    @Override
    public void checkBankAccountStatus() {
        BankomatMessage.message(Integer.toString(server.viewBalance(pinValidator)));
    }

    @Override
    public void withdrawMoney(int money) {
        server.getMoney(pinValidator, money);
    }

    @Override
    public void depositMoney(int money) {
        server.putMoney(pinValidator, money);
    }

    public long getValidatorTimeLeft() {
        return pinValidator.getTimeLeft();
    }
}
