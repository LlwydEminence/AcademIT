package gui;

import common.View;
import controller.Controller;
import model.Cash;

import javax.naming.OperationNotSupportedException;
import javax.swing.*;

public class FrameView implements View {
    private final static String DEPOSIT_CASH = "Внести наличные";
    private final static String WITHDRAW_CASH = "Снять наличные";
    private final static String DISPLAY_STATUS = "Отобразить состояние банкомата";
    private final static String CONTINUATION = "Продолжить работу с банкоматом?";
    private final static String[] NOTES = new String[] {"10", "50", "100", "500", "1000", "5000"};

    private Controller controller;

    public void startCashMachine() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                do{
                    initContent();
                } while (askToContinue() == JOptionPane.YES_OPTION);
                System.exit(0);
            }
        });
    }

    private void initContent() {
        String selection = (String) JOptionPane.showInputDialog(null, "Что нужно сделать?",
                "Банкомат", JOptionPane.QUESTION_MESSAGE, null,
                new String[] {DEPOSIT_CASH, WITHDRAW_CASH, DISPLAY_STATUS}, DEPOSIT_CASH);

        if (selection == null) {
            System.exit(0);
        } else if (selection.equals(DEPOSIT_CASH)) {
            String requiredDenomination = (String) JOptionPane.showInputDialog(null,
                    "Какими купюрами вы хотите внести наличные?", "Банкомат", JOptionPane.QUESTION_MESSAGE,
                    null, NOTES, NOTES[0]);

            if (requiredDenomination == null) {
                return;
            }

            int numberOfNotes;
            while (true) {
                try {
                    numberOfNotes = Integer.parseInt(JOptionPane.showInputDialog(null,
                            "Сколько купюр вы хотите внести?", "Банкомат", JOptionPane.QUESTION_MESSAGE));
                    break;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Количество должно быть целым числом!",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }

            try {
                controller.needDeposit(Integer.parseInt(requiredDenomination), numberOfNotes);
            } catch (OperationNotSupportedException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }

        } else if (selection.equals(WITHDRAW_CASH)) {
            int amount;
            while (true) {
                try {
                    amount = Integer.parseInt(JOptionPane.showInputDialog(null,
                            "Какую сумму вы хотите снять?", "Банкомат", JOptionPane.QUESTION_MESSAGE));
                    break;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Сумма должна быть целым числом!",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }

            String requiredDenomination = (String) JOptionPane.showInputDialog(null,
                    "Какими купюрами вы хотите получить сумму?", "Банкомат", JOptionPane.QUESTION_MESSAGE,
                    null, NOTES, NOTES[0]);

            if (requiredDenomination == null) {
                return;
            }

            try {
                controller.needWithdrawCash(amount, Integer.parseInt(requiredDenomination));
            } catch (OperationNotSupportedException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            controller.needData();
        }
    }

    public void addController(Controller controller) {
        this.controller = controller;
    }

    public void reportForDeposit(int amount) {
        String report = "Внесено " + amount + " рублей.";
        JOptionPane.showMessageDialog(null, report,"Внесено", JOptionPane.INFORMATION_MESSAGE);
    }

    public void reportForWithdrawCash(int amount) {
        String report = "Выдано " + amount + " рублей.";
        JOptionPane.showMessageDialog(null, report,"Внесено", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayStatus(int amount, Cash[] cash) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("В банокомате доступно ");
        stringBuilder.append(amount);
        stringBuilder.append(" рублей.");
        if (amount != 0) {
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append("В наличии купюры следующих номиналов:");
            for (Cash c : cash) {
                int cashNumber = c.getNumber();
                if (cashNumber != 0) {
                    stringBuilder.append(System.lineSeparator());
                    stringBuilder.append(cashNumber);
                    stringBuilder.append(" купюр");
                    stringBuilder.append(getEnding(cashNumber));
                    stringBuilder.append(" по ");
                    stringBuilder.append(c.getValue());
                    stringBuilder.append(" рублей");
                }
            }

            JOptionPane.showMessageDialog(null, stringBuilder.toString(),"Состояние банкомата", JOptionPane.INFORMATION_MESSAGE);
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

    private int askToContinue() {
        return JOptionPane.showConfirmDialog(null, CONTINUATION, null,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
}
