package ru.academits.streltsov.minesweeper.gui;

import ru.academits.streltsov.minesweeper.common.View;
import ru.academits.streltsov.minesweeper.conroller.Controller;
import ru.academits.streltsov.minesweeper.model.Cell;
import ru.academits.streltsov.minesweeper.model.HighScores;
import ru.academits.streltsov.minesweeper.model.Minesweeper;
import ru.academits.streltsov.minesweeper.model.Winner;

import javax.naming.OperationNotSupportedException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

public class GameView extends JFrame implements View {
    private static final ImageIcon FLAG =
            new ImageIcon(HighScores.RESOURCES_PATH + "/Flag.png");
    private static final ImageIcon BOMB =
            new ImageIcon(HighScores.RESOURCES_PATH + "/Bomb.png");
    private static final ImageIcon EXPLOSION =
            new ImageIcon(HighScores.RESOURCES_PATH + "/explosion.png");
    private static final ImageIcon WRONG_FLAG =
            new ImageIcon(HighScores.RESOURCES_PATH + "/wrongflag.png");
    private static final ImageIcon QUESTION =
            new ImageIcon(HighScores.RESOURCES_PATH + "/question.png");
    private static final Dimension CELL_SIZE = new Dimension(FLAG.getIconWidth(), FLAG.getIconHeight());
    private static final String EXIT_COMMAND = "Выход";

    private JPanel fieldPanel = new JPanel(new GridBagLayout());
    private JPanel informationPanel = new JPanel(new GridLayout(0, 2));
    private JPanel flagPanel = new JPanel();
    private JPanel timePanel = new JPanel();
    private Controller controller;

    private JLabel flagLabel = new JLabel();
    private int flagCount;
    private JLabel timeLabel = new JLabel();

    @Override
    public void startApplication() throws FileNotFoundException {
        initContent();
        initFrame();
    }

    private void reCreateContent() {
        fieldPanel.removeAll();
        flagCount = controller.getMinesNumber();
        flagLabel.setText(Integer.toString(flagCount));
        timeLabel.setText(millisecondsToDate(0));
        controller.printField();
        pack();
        repaint();
        revalidate();
    }

    private void initContent(String level) {
        if (!level.equals(Minesweeper.USER)) {
            controller.clearAll();
            controller.initField(level);
            reCreateContent();

        } else {
            UserSliderDialog dialog = new UserSliderDialog(this);
            dialog.setVisible(true);

            if (dialog.isFieldCreated()) {
                reCreateContent();
            } else {
                controller.renewStartTime();
                controller.startTimer();
            }
        }
    }

    private void initContent() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS));

        JMenu newGameItem = new JMenu("Игра");
        menuBar.add(newGameItem);

        ActionListener listener = e -> {
            JMenuItem source = (JMenuItem) e.getSource();
            initContent(source.getText());
        };

        JMenuItem beginner = newGameItem.add(Minesweeper.BEGINNER);
        beginner.addActionListener(listener);

        JMenuItem amateur = newGameItem.add(Minesweeper.AMATEUR);
        amateur.addActionListener(listener);

        JMenuItem expert = newGameItem.add(Minesweeper.EXPERT);
        expert.addActionListener(listener);

        JMenuItem user = newGameItem.add(Minesweeper.USER);
        user.addActionListener(listener);

        JMenuItem exit = newGameItem.add(EXIT_COMMAND);
        exit.addActionListener(e -> System.exit(0));

        JMenu highScoresItem = new JMenu("Таблица рекордов");
        highScoresItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (KeyPress.isLeftMouseButtonPressed(e)) {
                    try {
                        showHighScores();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        menuBar.add(highScoresItem);

        JMenu aboutItem = new JMenu
                ("Об игре");
        aboutItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (KeyPress.isLeftMouseButtonPressed(e)) {
                    JOptionPane.showMessageDialog(null, "Сапёр");
                }
            }
        });
        menuBar.add(aboutItem);
        setJMenuBar(menuBar);

        flagPanel.add(new Label("Флаги:"),
                new GBC(0, 0).setAnchor(GBC.EAST));
        flagPanel.add(flagLabel, new GBC(1, 0));
        timePanel.add(new Label("Время:"));
        timePanel.add(timeLabel);

        informationPanel.add(flagPanel);
        informationPanel.add(timePanel);
        initContent(Minesweeper.BEGINNER);
        add(fieldPanel, BorderLayout.CENTER);
        add(informationPanel, BorderLayout.NORTH);
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(500, 220);
        setResizable(false);
        setTitle("Сапер");
        pack();
        setVisible(true);
    }

    public void addController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void printField(Cell[][] cells) {
        for (int i = 0; i < cells.length; ++i) {
            for (int j = 0; j < cells[0].length; ++j) {
                CellButton button = new CellButton(i, j);
                button.setPreferredSize(CELL_SIZE);
                Cell cell = cells[i][j];

                if (!cell.isOpened()) {
                    if (!cell.isMarked() && !cell.isQuestioned()) {
                        button.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                if (KeyPress.isRightMouseButtonPressed(e)) {
                                    try {
                                        controller.setMark(button.getRow(), button.getColumn());
                                        --flagCount;
                                        flagLabel.setText(String.valueOf(flagCount));
                                        fieldPanel.removeAll();
                                        controller.printField();
                                    } catch (OperationNotSupportedException e1) {
                                        controller.printField();
                                    }
                                } else if (KeyPress.isLeftMouseButtonPressed(e)) {
                                    try {
                                        if (controller.openCell(button.getRow(), button.getColumn())) {
                                            return;
                                        }
                                        fieldPanel.removeAll();
                                        controller.printField();
                                    } catch (OperationNotSupportedException ex) {
                                        controller.printField();
                                    } catch (FileNotFoundException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        });
                    } else if (cell.isMarked()) {
                        button.setIcon(FLAG);
                        button.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                if (KeyPress.isRightMouseButtonPressed(e)) {
                                    try {
                                        controller.deleteMark(button.getRow(), button.getColumn());
                                        ++flagCount;
                                        flagLabel.setText(String.valueOf(flagCount));
                                        controller.setQuestion(button.getRow(), button.getColumn());
                                        fieldPanel.removeAll();
                                        controller.printField();
                                    } catch (OperationNotSupportedException e1) {
                                        controller.printField();
                                    }
                                }
                            }
                        });
                    } else if (cell.isQuestioned()) {
                        button.setIcon(QUESTION);
                        button.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                if (KeyPress.isRightMouseButtonPressed(e)) {
                                    try {
                                        controller.deleteQuestion(button.getRow(), button.getColumn());
                                        fieldPanel.removeAll();
                                        controller.printField();
                                    } catch (OperationNotSupportedException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                } else {
                    if (!cell.isNoMineNear()) {
                        button.setIcon(new ImageIcon(HighScores.RESOURCES_PATH + "/" +
                                cell.getValue() + ".png"));
                    } else {
                        button.setText(null);
                        button.setEnabled(false);
                    }

                    button.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            if (KeyPress.isMiddleMouseButtonPressed(e) ||
                                    KeyPress.isRightAndLeftMouseButtonsPressed(e)) {
                                try {
                                    if (controller.fastOpen(button.getRow(), button.getColumn())) {
                                        return;
                                    }
                                    fieldPanel.removeAll();
                                    controller.printField();
                                } catch (OperationNotSupportedException | IllegalArgumentException e1) {
                                    controller.printField();
                                } catch (FileNotFoundException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    });
                }

                fieldPanel.add(button, new GBC(j, i));
            }
        }
        Container contentPane = getContentPane();
        contentPane.repaint();
        contentPane.revalidate();
    }

    @Override
    public void printOpenedField(Cell[][] cells) {
        fieldPanel.removeAll();
        for (int i = 0; i < cells.length; ++i) {
            for (int j = 0; j < cells[0].length; ++j) {
                JButton button = new JButton();
                button.setPreferredSize(CELL_SIZE);
                Cell cell = cells[i][j];

                if (cell.isMine() && cell.isOpened()) {
                    button.setIcon(EXPLOSION);
                } else if (cell.isMine()) {
                    button.setIcon(BOMB);
                } else if (cell.isMarked() && !cell.isMine()) {
                    button.setIcon(WRONG_FLAG);
                } else if (cell.isMarked()) {
                    button.setIcon(FLAG);
                } else if (cell.isQuestioned()) {
                    button.setIcon(QUESTION);
                } else {
                    if (!cell.isNoMineNear()) {
                        button.setIcon(new ImageIcon("Minesweeper/src/ru/academits/streltsov/minesweeper/resources/" +
                                cell.getValue() + ".png"));
                    } else {
                        button.setText(null);
                        button.setEnabled(false);
                    }
                }

                fieldPanel.add(button, new GBC(j, i));
            }
        }
        Container contentPane = getContentPane();
        contentPane.repaint();
        contentPane.revalidate();
    }

    @Override
    public void showHighScores(String level) throws FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Winner> winners = controller.getHighScores(level);
        int i = 1;
        for (Winner winner : winners) {
            stringBuilder.append(i);
            stringBuilder.append(". ");
            stringBuilder.append(winner.getName());
            stringBuilder.append(" ");
            stringBuilder.append(millisecondsToDate(winner.getTime()));
            stringBuilder.append(System.lineSeparator());
            ++i;
        }
        JOptionPane.showMessageDialog(null, stringBuilder.toString(),
                level, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showHighScores() throws FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        for (String level : Minesweeper.LEVELS) {
            if (level.equals(Minesweeper.USER)) {
                continue;
            }

            stringBuilder.append(level);
            stringBuilder.append(":");
            stringBuilder.append(System.lineSeparator());
            ArrayList<Winner> winners = controller.getHighScores(level);
            int i = 1;
            for (Winner winner : winners) {
                String string = i + ". " + winner.getName() + " " + millisecondsToDate(winner.getTime());
                stringBuilder.append(string);
                stringBuilder.append(System.lineSeparator());
                ++i;
            }
            stringBuilder.append(System.lineSeparator());
        }

        JOptionPane.showMessageDialog(null, stringBuilder.toString(),
                "Таблица рекордов", JOptionPane.INFORMATION_MESSAGE);
    }

    private String millisecondsToDate(long millis) {
        long second = (millis / 1000) % 60;
        long minute = (millis / (1000 * 60)) % 60;
        long hour = (millis / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    @Override
    public void onVictory(long time) throws FileNotFoundException {
        controller.printOpenedField();
        String message = "Победа! Ваше время: " + millisecondsToDate(time);
        JOptionPane.showMessageDialog(null, message, "Победа!", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onGameOver() {
        controller.printOpenedField();
        JOptionPane.showMessageDialog(null, "Поражение!", "Поражение!", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public String getWinnerName() {
        while (true) {
            String winnerName = JOptionPane.showInputDialog(null, "Введите имя:",
                    "Поставлен рекорд", JOptionPane.QUESTION_MESSAGE);
            if (!Objects.equals(winnerName, "") && winnerName != null) {
                return winnerName;
            }
        }

    }

    @Override
    public void updateTimer(long time) {
        timeLabel.setText(millisecondsToDate(time));
    }

    Controller getController() {
        return controller;
    }
}
