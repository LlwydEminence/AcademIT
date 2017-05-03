package textui;

import controller.Controller;

import javax.naming.OperationNotSupportedException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleView {
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
                    controller.needDisplayStatus();
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
                System.out.println("Купюру каким номиналом вы хотите внести?. Для отмены нажмите " + FINISH);
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

                controller.needMakeCash(requiredDenomination, cashNumber);
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

    public void reportForMakeCash(int amount) {
        System.out.println("Внесено " + amount + " рублей.");
    }

    public void reportForWithdrawCash(int amount) {
        System.out.println("Выдано " + amount + " рублей.");
    }

    public void displayStatus(String string) {
        System.out.println(string);
    }

    public void addController(Controller controller) {
        this.controller = controller;
    }
}
