package gui;

import common.View;
import controller.Controller;
import model.Cash;

import javax.naming.OperationNotSupportedException;
import javax.swing.*;
import java.awt.*;

public class FrameView implements View {
    private final static String DEPOSIT_CASH = "Внести наличные";
    private final static String WITHDRAW_CASH = "Снять наличные";
    private final static String DISPLAY_STATUS = "Отобразить состояние банкомата";

    private final JFrame frame = new JFrame();
    private final JPanel panel = new JPanel(new GridBagLayout());
    private final JPanel buttonPanel = new JPanel();
    private final JLabel label = new JLabel("Количество вносимых купюр: ");
    private final JLabel denominationLabel = new JLabel("Выберите номинал: ");
    private final JButton button = new JButton("Внести");
    private final JTextField textField = new JTextField(5);
    private final JComboBox<String> modeComboBox = new JComboBox<>(new String[] {DEPOSIT_CASH, WITHDRAW_CASH, DISPLAY_STATUS});
    private JComboBox<Integer> comboBox;
    private Controller controller;

    public void startCashMachine() {
        SwingUtilities.invokeLater(() -> {
            initContent();
            initFrame();
            initEvents();
        });
    }

    private void initContent() {
        comboBox = new JComboBox<>(controller.needDenominations());
        comboBox.setSelectedIndex(0);
        panel.add(label, new GBC(0, 0).setAnchor(GridBagConstraints.WEST));
        panel.add(textField, new GBC(1,0));
        panel.add(denominationLabel, new GBC(0, 1).setAnchor(GridBagConstraints.WEST));
        panel.add(comboBox, new GBC(1, 1));
        buttonPanel.add(button);
    }

    private void initFrame() {
        modeComboBox.setSelectedIndex(0);
        frame.add(modeComboBox, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void initEvents() {
        modeComboBox.addActionListener(e -> {
            switch (modeComboBox.getItemAt(modeComboBox.getSelectedIndex())) {
                case DEPOSIT_CASH: {
                    if (!panel.isVisible()) {
                        panel.setVisible(true);
                        frame.pack();
                    }

                    label.setText("Количество вносимых купюр: ");
                    textField.setText(null);
                    comboBox.setSelectedIndex(0);
                    button.setText("Внести");
                    break;
                }

                case WITHDRAW_CASH: {
                    if (!panel.isVisible()) {
                        panel.setVisible(true);
                        frame.pack();
                    }

                    label.setText("Введите сумму: ");
                    textField.setText(null);
                    comboBox.setSelectedIndex(0);
                    button.setText("Выдать");
                    break;
                }

                case DISPLAY_STATUS: {
                    panel.setVisible(false);
                    frame.pack();
                    button.setText("Отобразить");
                    break;
                }
            }
        });

        button.addActionListener(e -> {
            try {
                String selectedItem = modeComboBox.getItemAt(modeComboBox.getSelectedIndex());
                switch (selectedItem) {
                    case DEPOSIT_CASH: {
                        controller.needDeposit(comboBox.getItemAt(comboBox.getSelectedIndex()),
                                Integer.parseInt(textField.getText()));
                        break;
                    }

                    case WITHDRAW_CASH: {
                        controller.needWithdrawCash(Integer.parseInt(textField.getText()),
                                comboBox.getItemAt(comboBox.getSelectedIndex()));
                        break;
                    }

                    default: {
                        controller.needData();
                        break;
                    }
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Вводимая величина должна быть целым положительным числом",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (OperationNotSupportedException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
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

    public void displayStatus(int amount, int notesNumber, Cash[] cash) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("В банокомате доступно ");
        stringBuilder.append(amount);
        stringBuilder.append(" рублей.");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Доступно купюр для внесения: ");
        stringBuilder.append(notesNumber);
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
        if (notesNumber % 10 == 1 && (notesNumber - 11) % 100 != 0) {
            return  "а";
        } else if ((notesNumber % 10 == 2 && (notesNumber - 12) % 100 != 0) ||
                (notesNumber % 10 == 3 && (notesNumber - 13) % 100 != 0)||
                (notesNumber % 10 == 4 && (notesNumber - 14) % 100 != 0)) {
            return  "ы";
        } else {
            return "";
        }
    }
}
