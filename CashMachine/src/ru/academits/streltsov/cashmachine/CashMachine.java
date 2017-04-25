package ru.academits.streltsov.cashmachine;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CashMachine {
    private static final int DEFAULT_NOTE_NUMBER = 30;
    private static final int DENOMINATION_NUMBER = Denomination.values().length;
    private static final int MAX_NOTES_NUMBER = DENOMINATION_NUMBER * DEFAULT_NOTE_NUMBER;
    private static final int MAX_WITHDRAWAL_AMOUNT = 20000;

    private ArrayList<Cash> notes;
    private int notesNumber;
    private int amountOfMoney;

    public CashMachine() {
        notes = new ArrayList<>(DENOMINATION_NUMBER);
        for (Denomination d : Denomination.values()) {
            notes.add(new Cash(d, DEFAULT_NOTE_NUMBER));
            notesNumber += DEFAULT_NOTE_NUMBER;
            amountOfMoney += d.getValue() * DEFAULT_NOTE_NUMBER;
        }
    }

    private boolean checkOverflow() {
        return notesNumber == MAX_NOTES_NUMBER;
    }

    private boolean checkAvailability() {
        return amountOfMoney > 0;
    }

    private void makeCash() throws OperationNotSupportedException {
        if (checkOverflow()) {
            throw new OperationNotSupportedException("Банкомат переполнен. Вносить деньги больше нельзя.");
        }

        while (true) {
            System.out.println("Купюру каким номиналом вы хотите внести?");
            Denomination[] denominations = Denomination.values();

            for (Denomination d : denominations) {
                System.out.println(d.getValue() + " рублей - нажмите " + d.getIndex());
            }
            System.out.println("Для отмены нажмите 0");
            Scanner scanner = new Scanner(System.in);

            int requiredIndex;
            try {
                requiredIndex = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Неправильный ввод.");
                continue;
            }

            if (requiredIndex == 0) {
                return;
            } else if (requiredIndex >= denominations[0].getIndex() &&
                    requiredIndex <= DENOMINATION_NUMBER) {
                int cashNumber;
                while (true) {
                    System.out.println("Сколько купюр вы хотите внести? Для отмены нажмите 0");

                    try {
                        cashNumber = scanner.nextInt();
                    }catch (InputMismatchException e) {
                        System.out.println("Неправильный ввод.");
                        continue;
                    }

                    if (cashNumber < 0) {
                        System.out.println("Число купюр должно быть положительным числом");
                    } else if (cashNumber == 0) {
                        return;
                    } else if (notesNumber + cashNumber > MAX_NOTES_NUMBER) {
                        System.out.println("Банкомат близок к переполнению. Максимально возможное количество купюр для внесения: " + (MAX_NOTES_NUMBER - notesNumber));
                    } else {
                        break;
                    }
                }

                make( requiredIndex - 1, cashNumber);
                System.out.println("*Внесено " + cashNumber + " купюр" + getEnding(cashNumber) + " по "
                        + notes.get(requiredIndex - 1).getValue() + " рублей.");
                return;
            } else {
                System.out.println("Неверный ввод");
            }
        }
    }

    private void make(int requiredIndex, int cashNumber) {
        Cash requiredCash = notes.get(requiredIndex);
        requiredCash.increaseNumber(cashNumber);
        notesNumber += cashNumber;
        int amount = cashNumber * requiredCash.getValue();
        amountOfMoney += amount;
    }

    private int[] changeBalance(int requiredIndex, int residualAmount, int counter) throws OperationNotSupportedException {
        int[] array = new int[DENOMINATION_NUMBER];
        array[requiredIndex] = counter;
        int currentAmount = 0;
        for (int i = array.length - 1; i >= 0; --i) {
            if (i != requiredIndex) {
                Cash cash = notes.get(i);
                int cashValue = cash.getValue();
                int cashNumber = cash.getNumber();
                int notesCounter;
                for (notesCounter = 1; notesCounter <= cashNumber; ++notesCounter) {
                    if (currentAmount + cashValue <= residualAmount) {
                        currentAmount += cashValue;
                    } else {
                        break;
                    }
                }
                --notesCounter;
                array[i] = notesCounter;

                if (currentAmount == residualAmount) {
                    return array;
                }
            }
        }

        throw new OperationNotSupportedException("Выдать остальную часть разменом невозможно. Попробуйте другую сумму.");
    }

    private int[] withdraw(int requiredIndex, int requiredAmount) {
        int availableAmount = 0;
        int notesCounter;
        Cash requiredCash = notes.get(requiredIndex);
        int cashValue = requiredCash.getValue();
        int cashNumber = requiredCash.getNumber();

        for (notesCounter = 1; notesCounter <= cashNumber; ++notesCounter) {
            if (availableAmount + cashValue <= requiredAmount) {
                availableAmount += cashValue;
            } else {
                break;
            }
        }
        --notesCounter;

        int residualAmount = requiredAmount - availableAmount;
        int[] countArray = new int[notes.size()];
        countArray[requiredIndex] = notesCounter;

        if (residualAmount > 0) {
            while (true) {
                System.out.println("Купюр с требуемым номиналом не хватает. Выдать остальную сумму другими купюрами?\n1 - да\n2 - нет");

                Scanner scanner = new Scanner(System.in);
                int number;

                try {
                    number = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Неправильный ввод.");
                    continue;
                }

                if (number == 1) {
                    try {
                        System.arraycopy(changeBalance(requiredIndex, residualAmount, notesCounter), 0, countArray, 0, countArray.length);

                        for (int i = 0; i < countArray.length; ++i) {
                            if (i != requiredIndex) {
                                Cash cash = notes.get(i);
                                cash.decreaseNumber(countArray[i]);
                                notesNumber -= countArray[i];
                            }
                        }

                        deductAvailableAmount(requiredCash, notesCounter, requiredAmount);
                        return countArray;
                    } catch (OperationNotSupportedException e) {
                        System.out.println(e.getMessage());
                        deductAvailableAmount(requiredCash, notesCounter, availableAmount);
                        return countArray;
                    }
                } else if (number == 2) {
                    deductAvailableAmount(requiredCash, notesCounter, availableAmount);
                    return countArray;
                } else {
                    System.out.println("Неверный ввод");
                }
            }
        } else {
            deductAvailableAmount(requiredCash, notesCounter, requiredAmount);
            return countArray;
        }
    }

    private void deductAvailableAmount(Cash requiredCash, int notesCounter, int availableAmount) {
        requiredCash.decreaseNumber(notesCounter);
        notesNumber -= notesCounter;
        amountOfMoney -= availableAmount;
    }

    private void withdrawCash() throws OperationNotSupportedException {
        if (!checkAvailability()) {
            throw new OperationNotSupportedException("В банкомате нет денег.");
        }

        while (true) {
            System.out.println("Введите сумму не более " + MAX_WITHDRAWAL_AMOUNT + " и кратную 10. Для отмены нажмите 0");

            Scanner scanner = new Scanner(System.in);
            int requiredAmount;

            try {
                requiredAmount = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Неправильный ввод.");
                continue;
            }

            if (requiredAmount == 0) {
                return;
            } else if (requiredAmount % 10 != 0 || requiredAmount > MAX_WITHDRAWAL_AMOUNT) {
                System.out.println("Неверно введена сумма.");
            } else if (requiredAmount <= amountOfMoney) {
                while (true) {
                    Denomination[] denominations = Denomination.values();
                    int lastIndex = 0;

                    boolean hasNotes = false;
                    for (Denomination d : denominations) {
                        int denominationValue = d.getValue();
                        int denominationIndex = d.getIndex();
                        if (denominationValue <= requiredAmount && notes.get(denominationIndex - 1).getNumber() != 0) {
                            if (!hasNotes) {
                                System.out.println("Какими купюрами выдать введенную сумму?");
                                hasNotes = true;
                            }
                            System.out.println(denominationValue + " рублей - нажмите " + denominationIndex);
                            lastIndex = denominationIndex;
                        }
                    }

                    if (lastIndex == 0) {
                        System.out.println("Ошибка! В банкомате отсутствуют купюры подходящих номиналов. Попробуйте снять большую сумму");
                        return;
                    }
                    System.out.println("Для отмены нажмите 0");

                    int requiredIndex;
                    try {
                        requiredIndex = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Неправильный ввод.");
                        continue;
                    }

                    if (requiredIndex == 0) {
                        return;
                    } else if (requiredIndex >= denominations[0].getIndex() && requiredIndex <= lastIndex) {
                        int[] result = withdraw(requiredIndex - 1, requiredAmount);
                        for (int i = 0; i < notes.size(); ++i ) {
                            if (result[i] != 0) {
                                System.out.println("*Выдано " + result[i] + " купюр" + getEnding(result[i])
                                        + " по " + notes.get(i).getValue() + " рублей*");
                            }
                        }
                        return;
                    } else {
                        System.out.println("Неверный ввод");
                    }
                }
            } else {
                System.out.println("В банкомате не хватает денег. Минимально доступная сумма: " + amountOfMoney);
            }
        }
    }

    private void displayStatus() {
        System.out.println();
        System.out.println("В банкомате доступно " + amountOfMoney + " рублей.");
        System.out.println("В наличии купюры номиналов: ");

        for (Cash cash : notes) {
            int cashNumber = cash.getNumber();
            if (cashNumber != 0) {
                System.out.println(cash.getValue() + " рублей. " + cashNumber + " купюр" + getEnding(cashNumber));
            }
        }
    }

    private String getEnding(int cashNumber) {
        if (cashNumber % 10 == 1) {
            return  "а";
        } else if (cashNumber % 10 == 2 || cashNumber % 10 == 3 || cashNumber % 10 == 4) {
            return  "ы";
        } else {
            return "";
        }
    }

    public void menu() {
        while (true) {
            System.out.println();
            System.out.println("Внести наличные - нажмите 1");
            System.out.println("Снять наличные - нажмите 2");
            System.out.println("Отобразить состояние банкомата - нажмите 3");
            System.out.println("Закончить работу с банкоматом - нажмите 0");

            Scanner scanner = new Scanner(System.in);
            int number;

            try {
                number = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Неправильный ввод.");
                continue;
            }

            switch (number) {
                case 1: {
                    try {
                        makeCash();
                        break;
                    } catch (OperationNotSupportedException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                }

                case 2: {
                    try {
                        withdrawCash();
                        break;
                    } catch (OperationNotSupportedException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                }

                case 3: {
                    displayStatus();
                    break;
                }

                case 0: {
                    return;
                }

                default: {
                    System.out.println("Неверный ввод.");
                }
            }
        }
    }
}
