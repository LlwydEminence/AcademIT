package ru.academits.streltsov.minesweeper.gui;

import ru.academits.streltsov.minesweeper.common.View;
import ru.academits.streltsov.minesweeper.conroller.Controller;
import ru.academits.streltsov.minesweeper.model.Cell;
import ru.academits.streltsov.minesweeper.model.Minesweeper;
import ru.academits.streltsov.minesweeper.model.Winner;

import javax.naming.OperationNotSupportedException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameView extends JFrame implements View {
    private static final ImageIcon FLAG =
            new ImageIcon("Minesweeper/src/ru/academits/streltsov/minesweeper/resources/Flag.png");
    private static final ImageIcon BOMB =
            new ImageIcon("Minesweeper/src/ru/academits/streltsov/minesweeper/resources/Bomb.png");
    private static final ImageIcon EXPLOSION =
            new ImageIcon("Minesweeper/src/ru/academits/streltsov/minesweeper/resources/explosion.png");
    private static final ImageIcon WRONG_FLAG =
            new ImageIcon("Minesweeper/src/ru/academits/streltsov/minesweeper/resources/wrongflag.png");
    private static final ImageIcon QUESTION =
            new ImageIcon("Minesweeper/src/ru/academits/streltsov/minesweeper/resources/question.png");
    private static final Dimension CELL_SIZE = new Dimension(FLAG.getIconWidth(), FLAG.getIconHeight());

    private final ChoiceFrame choiceFrame;
    private final JPanel fieldPanel = new JPanel(new GridBagLayout());
    private Controller controller;

    private JLabel flagLabel;
    private int flagCount;
    private JLabel timeLabel;

    GameView(ChoiceFrame choiceFrame) {
        this.choiceFrame = choiceFrame;
    }

    @Override
    public void startApplication() throws FileNotFoundException {
        initContent();
        initFrame();
    }

    private void initContent() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS));

        JMenu newGameItem = new JMenu("Новая игра");
        newGameItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
                    choiceFrame.setVisible(true);
                    setVisible(false);
                }
            }
        });
        menuBar.add(newGameItem);

        JMenu highScoresItem = new JMenu("Таблица рекордов");
        highScoresItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
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
                if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
                    JOptionPane.showMessageDialog(null, "Сапёр");
                }
            }
        });
        menuBar.add(aboutItem);

        JPanel flagPanel = new JPanel(new GridBagLayout());
        flagPanel.add(new Label("Флаги:"),
                new GBC(0, 0).setAnchor(GBC.EAST));

        flagCount = controller.getMinesNumber();
        flagLabel = new JLabel(String.valueOf(flagCount));
        flagPanel.add(flagLabel, new GBC(1, 0));

        JPanel timePanel = new JPanel();
        timePanel.add(new Label("Время:"));
        timeLabel = new JLabel(millisecondsToDate(0));
        timePanel.add(timeLabel);

        JPanel informationPanel = new JPanel(new GridLayout(0, 2));
        informationPanel.add(flagPanel);
        informationPanel.add(timePanel);

        setJMenuBar(menuBar);
        add(informationPanel, BorderLayout.NORTH);
        controller.printField();
        add(fieldPanel, BorderLayout.CENTER);
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
                                if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0) {
                                    try {
                                        controller.setMark(button.getRow(), button.getColumn());
                                        --flagCount;
                                        flagLabel.setText(String.valueOf(flagCount));
                                        fieldPanel.removeAll();
                                        controller.printField();
                                    } catch (OperationNotSupportedException e1) {
                                        controller.printField();
                                    }
                                } else if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
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
                                if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0) {
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
                                if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0) {
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
                        button.setIcon(new ImageIcon("Minesweeper/src/ru/academits/streltsov/minesweeper/resources/" +
                                cell.getValue() + ".png"));
                    } else {
                        button.setText(null);
                        button.setEnabled(false);
                    }

                    button.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            if ((e.getModifiersEx() & InputEvent.BUTTON2_DOWN_MASK) != 0 ||
                                    (((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) &&
                                            ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0))) {
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
            String string = i + ". " + winner.getName() + " " + millisecondsToDate(winner.getTime());
            stringBuilder.append(string);
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
        return JOptionPane.showInputDialog(null, "Введите имя:",
                "Поставлен рекорд", JOptionPane.QUESTION_MESSAGE);
    }

    @Override
    public void updateTimer(long time) {
        timeLabel.setText(millisecondsToDate(time));
    }
}
