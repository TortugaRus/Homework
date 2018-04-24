package ru.sberbank.homework.kvasov;

public class PinValidator {

    private String pin;
    private int numberOfTryValidate = 0;
    private long timeLock;
    private long TIMEOUT = 5000;
    private boolean isAccountLock;
    private boolean isValidated;


    public PinValidator(String pin) {
        this.pin = pin;
    }

    public void checkValidate(String pin) {
        if (isAccountLock) {
            if (System.currentTimeMillis() - timeLock > TIMEOUT) {
                isAccountLock = false;
                numberOfTryValidate = 0;
            } else {
                throw new AccountIsLockedException();
            }
        }
        isValidated = this.pin.equals(pin);
        if (!isValidated) {
            numberOfTryValidate++;
            if (numberOfTryValidate >= 3) {
                isAccountLock = true;
                timeLock = System.currentTimeMillis();
            }
            throw new PinValidatorException();
        }
    }

    public boolean validate() {
        return isValidated;
    }

    public long getTimeLeft() {
        long timeLeft = (TIMEOUT - (System.currentTimeMillis() - timeLock)) / 1000L;
        return timeLeft;
    }
}
