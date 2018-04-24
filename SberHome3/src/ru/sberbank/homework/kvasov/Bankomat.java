package ru.sberbank.homework.kvasov;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Bankomat {
    private TerminalImpl terminal = new TerminalImpl();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        BankomatMessage.message("АТМ Сбербанк." + '\n' + "Введите пин-код:");
        while (true) {
            try {
                String pin = scanner.nextLine();
                if (terminal.pinValidation(pin)) {
                    break;
                }
            } catch (AccountIsLockedException accountException) {
                BankomatMessage.message("Аккаунт заблокирован! Повторите через " +
                        terminal.getValidatorTimeLeft() + " сек.");
            } catch (PinValidatorException validatorException) {
                BankomatMessage.message("Неправильный пин-код! Повторите ввод.");
            }
        }

        while (true) {
            try {
                BankomatMessage.message("1. Проверить баланс");
                BankomatMessage.message("2. Положить наличные");
                BankomatMessage.message("3. Снять наличные");
                BankomatMessage.message("4. Выход");
                BankomatMessage.message("Выберите желаемую операцию (Номер операции):");

                String operation = scanner.nextLine();
                switch (operation) {
                    case "1":
                        checkBalance();
                        break;
                    case "2":
                        putMoney();
                        break;
                    case "3":
                        withdrawMoney();
                        break;
                    case "4":
                        exitBankomat();
                        scanner.close();
                        return;
                    default:
                        BankomatMessage.message("Неверная операция. Повторите снова.");
                }
            } catch (AccountIsLockedException accountException) {
                BankomatMessage.message("Аккаунт заблокирован! Повторите через " + terminal.getValidatorTimeLeft() + " сек.");
            } catch (PinValidatorException validatorException) {
                BankomatMessage.message("Неправильный пин-код! Повторите ввод.");
            } catch (TerminalServerException serverException) {
                BankomatMessage.message("Нет доступа к серверу. Попробуйте позже.");
            } catch (NotEnoughMoneyException moneyException) {
                BankomatMessage.message("Недостаточно средств для снятия наличных!");
            }
        }
    }

    private void checkBalance(){
        BankomatMessage.message("/////Операция проверки баланса/////");
        BankomatMessage.message("Текущий баланс:");
        terminal.checkBankAccountStatus();
        exitInMenu();
    }


    private void putMoney() {
        BankomatMessage.message("/////Операция зачисления средств/////");
        BankomatMessage.message("Введите необходимую сумму для зачисления:");
        while (true) {
            try {
                int putMoney = Integer.parseInt(scanner.nextLine());
                if (putMoney % 100 == 0) {
                    terminal.depositMoney(putMoney);
                    BankomatMessage.message("Успешно зачислено.");
                    BankomatMessage.message("Текущий баланс:");
                    terminal.checkBankAccountStatus();
                    break;
                } else {
                    BankomatMessage.message("Сумма средств должна быть кратна 100!");
                }
            } catch (Exception ex) {
                BankomatMessage.message("Некорректный формат числа. Повторите снова");
            }
        }
        exitInMenu();
    }

    private void withdrawMoney() {
        BankomatMessage.message("/////Операция снятия средств/////");
        BankomatMessage.message("Введите необходимую сумму для снятия:");
        while (true) {
            try {
                int withdrawMoney = Integer.parseInt(scanner.nextLine());
                if (withdrawMoney % 100 == 0) {
                    terminal.withdrawMoney(withdrawMoney);
                    BankomatMessage.message("Успешное снятие наличных.");
                    BankomatMessage.message("Текущий баланс:");
                    terminal.checkBankAccountStatus();
                    break;
                } else {
                    BankomatMessage.message("Сумма средств должна быть кратна 100!");
                }
            } catch (Exception ex) {
                BankomatMessage.message("Некорректный формат числа. Повторите снова");
            }
        }
        exitInMenu();
    }

    private void exitInMenu() {
        BankomatMessage.message("Для выхода в меню введите \"меню\" ");
        while (true) {
            String message = scanner.nextLine();
            if (!message.equals("меню")) {
                System.out.println("Некорректная команда");
            } else {
                return;
            }
        }
    }

    private void exitBankomat() {
        BankomatMessage.message("Выход.");
    }
}
