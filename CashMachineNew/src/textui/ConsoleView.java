package textui;

import common.View;
import controller.Controller;
import model.Cash;

import javax.naming.OperationNotSupportedException;
import java.util.Scanner;

public class ConsoleView implements View {
    private Scanner scanner = new Scanner(System.in);
    private Controller controller;
    private final static String MAKE_CASH = "1";
    private final static String WITHDRAW_CASH = "2";
    private final static String DISPLAY_STATUS = "3";
    private final static String FINISH = "0";

    public void startCashMachine() {
        while (true) {
            System.out.println();
            System.out.println("Внести наличные - нажмите " + MAKE_CASH);
            System.out.println("Снять наличные - нажмите " + WITHDRAW_CASH);
            System.out.println("Отобразить состояние банкомата - нажмите " + DISPLAY_STATUS);
            System.out.println("Закончить работу с банкоматом - нажмите " + FINISH);

            String choice = scanner.nextLine();
            switch (choice) {
                case MAKE_CASH: {
                    clarifyRequestForMakeCash();
                    break;
                }

                case WITHDRAW_CASH: {
                    clarifyRequestForWithdrawCash();
                    break;
                }

                case DISPLAY_STATUS: {
                    controller.needData();
                    break;
                }

                case FINISH: {
                    return;
                }

                default: {
                    System.out.println("Неверный ввод.");
                }
            }
        }
    }

    private void clarifyRequestForMakeCash() {
        while (true) {
            try {
                System.out.println("Купюру каким номиналом вы хотите внести? Для отмены нажмите " + FINISH);
                int requiredDenomination = Integer.parseInt(scanner.nextLine());

                if (requiredDenomination == Integer.parseInt(FINISH)) {
                    return;
                }

                int cashNumber;
                while (true) {
                    System.out.println("Сколько купюр вы хотите внести?. Для отмены нажмите " + FINISH);
                    try {
                        cashNumber = Integer.parseInt(scanner.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Должно быть целое число.");
                    }
                }

                if (cashNumber == Integer.parseInt(FINISH)) {
                    return;
                }

                controller.needDeposit(requiredDenomination, cashNumber);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Должно быть целое число.");
            } catch (OperationNotSupportedException e) {
                System.out.println(e.getMessage());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void clarifyRequestForWithdrawCash() {
        while (true) {
            try {
                System.out.println("Введите сумму кратную 10. Для отмены нажмите 0.");
                int requiredAmount = Integer.parseInt(scanner.nextLine());

                if (requiredAmount == Integer.parseInt(FINISH)) {
                    return;
                }

                int requiredDenomination;
                while (true) {
                    try {
                        System.out.println("Какими купюрами выдать введенную сумму? Для отмены нажмите 0.");
                        requiredDenomination = Integer.parseInt(scanner.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Должно быть целое число.");
                    }
                }

                if (requiredDenomination == Integer.parseInt(FINISH)) {
                    return;
                }

                controller.needWithdrawCash(requiredAmount, requiredDenomination);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Должно быть целое число.");
            } catch (OperationNotSupportedException e) {
                System.out.println(e.getMessage());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void reportForDeposit(int amount) {
        System.out.println("Внесено " + amount + " рублей.");
    }

    public void reportForWithdrawCash(int amount) {
        System.out.println("Выдано " + amount + " рублей.");
    }

    public void displayStatus(int amountOfMoney, int notesNumber, Cash[] cash) {
        System.out.println("В банкомате доступно " + amountOfMoney + " рублей.");
        if (amountOfMoney != 0) {
            System.out.println("В наличии купюры следующих номиналов:");
            for (Cash c : cash) {
                int cashNumber = c.getNumber();
                if (cashNumber != 0) {
                    System.out.println(cashNumber + " купюр" + getEnding(cashNumber) + " по " +
                            c.getValue() + " рублей.");
                }
            }
        }
    }

    private String getEnding(int notesNumber) {
        if (notesNumber % 10 == 1) {
            return  "а";
        } else if (notesNumber % 10 == 2 || notesNumber % 10 == 3 || notesNumber % 10 == 4) {
            return  "ы";
        } else {
            return "";
        }
    }

    public void addController(Controller controller) {
        this.controller = controller;
    }
}
