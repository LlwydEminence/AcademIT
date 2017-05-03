package model;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class CashMachine {
    private static final int DEFAULT_NOTE_NUMBER = 30;
    private static final Denomination[] DENOMINATIONS = Denomination.values();
    private static final int DENOMINATION_NUMBER = DENOMINATIONS.length;
    private static final int MAX_NOTES_NUMBER = DENOMINATION_NUMBER * DEFAULT_NOTE_NUMBER;

    private ArrayList<Cash> notes;
    private int notesNumber;
    private int amountOfMoney;

    public CashMachine() {
        notes = new ArrayList<>();
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

    private Cash checkCash(int requiredDenomination) {
        for (Denomination denomination: DENOMINATIONS) {
            if (requiredDenomination == denomination.getValue()) {
                return notes.get(denomination.getIndex());
            }
        }

        throw new IllegalArgumentException("Купюр с таким номиналом не существует.");
    }

    public int makeCash(int requiredDenomination, int cashNumber) throws OperationNotSupportedException {
        if (checkOverflow()) {
            throw new OperationNotSupportedException("Банкомат переполнен. На данный момент внесение наличных невозможно.");
        }

        Cash requiredCash = checkCash(requiredDenomination);

        if (cashNumber <= 0) {
            throw new IllegalArgumentException("Число купюр должно быть больше нуля.");
        }

        if (notesNumber + cashNumber > MAX_NOTES_NUMBER) {
            throw new IllegalArgumentException("Банкомат близок к переполнению. " +
                    "Максимально возможное число купюр для внесения: " + (MAX_NOTES_NUMBER - notesNumber));
        }

        requiredCash.increaseNumber(cashNumber);
        notesNumber += cashNumber;
        int increasingAmount = cashNumber * requiredCash.getValue();
        amountOfMoney += increasingAmount;
        return increasingAmount;
    }

    public int withdrawCash(int requiredAmount, int requiredDenomination) throws OperationNotSupportedException {
        Cash requiredCash = checkCash(requiredDenomination);

        if (requiredAmount <= 0 || requiredAmount % 10 != 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной и кратной десяти.");
        }

        if (!checkAvailability()) {
            throw new OperationNotSupportedException("В банкомате отсутствуют наличные.");
        }

        if (requiredAmount > amountOfMoney) {
            throw new OperationNotSupportedException("Выдача указанной суммы невозможна. " +
                    "Доступная для выдачи сумма: " + amountOfMoney);
        }

        int cashValue = requiredCash.getValue();
        int availableAmount = requiredCash.getNumber() * cashValue;

        if (availableAmount < requiredAmount) {
            throw new OperationNotSupportedException("Выдача указанной суммы " + requiredDenomination +
                    " рублевыми купюрами невозможна. Максимальная сумма для выдачи такими купюрами: " + availableAmount);
        }

        if (requiredAmount % cashValue != 0) {
            throw new IllegalArgumentException("Введенная сумма не кратна номиналу зарпашиваемой купюры.");
        }

        int deductibleCashNumber = requiredAmount / cashValue;
        requiredCash.decreaseNumber(deductibleCashNumber);
        notesNumber -= deductibleCashNumber;
        amountOfMoney -= requiredAmount;
        return requiredAmount;
    }

    public String displayStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("В банкомате доступно ");
        stringBuilder.append(amountOfMoney);
        stringBuilder.append(" рублей.");

        if (amountOfMoney == 0) {
            return stringBuilder.toString();
        }

        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("В наличии купюры номиналов: ");
        stringBuilder.append(System.lineSeparator());

        for (Cash cash : notes) {
            int cashNumber = cash.getNumber();
            if (cashNumber != 0) {
                stringBuilder.append(cashNumber);
                stringBuilder.append(" купюр");
                stringBuilder.append(getEnding(cashNumber));
                stringBuilder.append(" по ");
                stringBuilder.append(cash.getValue());
                stringBuilder.append(" рублей");
                stringBuilder.append(System.lineSeparator());
            }
        }

        return stringBuilder.toString();
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
}
