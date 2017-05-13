package ru.academits.streltsov.minesweeper.textui;

import ru.academits.streltsov.minesweeper.common.View;
import ru.academits.streltsov.minesweeper.conroller.Controller;
import ru.academits.streltsov.minesweeper.model.*;

import javax.naming.OperationNotSupportedException;
import java.io.FileNotFoundException;
import java.util.*;

public class ConsoleView implements View {
    private Controller controller;
    private final Scanner scanner = new Scanner(System.in);

    public void startApplication() throws FileNotFoundException {
        while (true) {
            System.out.println("Новая игра - нажмите 1;");
            System.out.println("Таблица рекордов - нажмите 2;");
            System.out.println("Об игре - нажмите 3;");
            System.out.println("Выход - нажмите 0.");

            while (true) {
                String choice = scanner.next();
                switch (choice) {
                    case "1": {
                        newGame();
                        break;
                    }

                    case "2": {
                        highScores();
                        break;
                    }

                    case "3": {
                        about();
                        break;
                    }

                    case "0": {
                        System.exit(0);
                    }

                    default: {
                        System.out.println("Неверный ввод");
                        continue;
                    }
                }

                break;
            }
        }
    }

    public void addController(Controller controller) {
        this.controller = controller;
    }

    public void printField(Cell[][] cells) {
        for (Cell[] row : cells) {
            for (int j = 0; j < cells[0].length; ++j) {
                if (row[j].isOpened() && row[j].getValue() == 0) {
                    System.out.print("  ");
                } else if (row[j].isOpened()) {
                    System.out.printf("%d ", row[j].getValue());
                } else if (row[j].isMarked()) {
                    System.out.print("P ");
                } else {
                    System.out.print("X ");
                }

                if (j == cells[0].length - 1) {
                    System.out.println();
                }
            }
        }
    }

    public void printOpenedField(Cell[][] cells) {
        for (Cell[] row : cells) {
            for (int j = 0; j < cells[0].length; ++j) {
                if (row[j].getValue() == -1) {
                    System.out.print("M ");
                } else if (row[j].getValue() == 0) {
                    System.out.print("  ");
                } else {
                    System.out.printf("%d ", row[j].getValue());
                }

                if (j == cells[0].length - 1) {
                    System.out.println();
                }
            }
        }
    }

    private int getEnteredRow() {
        while (true) {
            System.out.print("Введите номер строки: ");
            try {
                return Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Должно быть число!");
            }
        }
    }

    private int getEnteredColumn() {
        while (true) {
            System.out.print("Введите номер столбца: ");
            try {
                return Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Должно быть целое число!");
            }
        }
    }

    private void newGame() throws FileNotFoundException {
        controller.needInitField(levelSelection(controller.needLevels()));
        controller.needPrintField();

        long start;
        long finish;
        while (true) {
            System.out.println("Открыть ячейку - нажмите 1;");
            System.out.println("Закончить игру - нажмите 0.");
            String choice = scanner.next();
            switch (choice) {
                case "1": {
                    controller.needOpenFirstCell(getEnteredRow(), getEnteredColumn());
                    start = System.currentTimeMillis();
                    break;
                }

                case "0": {
                    return;
                }

                default: {
                    System.out.println("Неверный ввод.");
                    continue;
                }
            }
            break;
        }

        while (true) {
            controller.needPrintField();
            System.out.println("Открыть ячейку - нажмите 1;");
            System.out.println("Пометить ячейку - нажмите 2;");
            System.out.println("Снять пометку - нажмите 3;");
            System.out.println("Закончить игру - нажмите 0;");

            String choice = scanner.next();
            switch (choice) {
                case "1": {
                    try {
                        controller.needOpenCell(getEnteredRow(), getEnteredColumn());
                        break;
                    } catch (OperationNotSupportedException e) {
                        System.out.println(e.getMessage());
                        continue;
                    } catch (GameOverException e) {
                        System.out.println(e.getMessage());
                        controller.needPrintOpenedField();
                        return;
                    } catch (VictoryException e) {
                        finish = System.currentTimeMillis();
                        long time = finish - start;

                        System.out.println(e.getMessage());
                        System.out.println("Ваше время: " + millisecondsToDate(time));

                        ArrayList<Winner> winners = controller.needHighScores();
                        if (winners.isEmpty() || winners.get(winners.size() - 1).getTime() >= time) {
                            System.out.print("Введите ваше имя: ");
                            controller.needAddWinner(scanner.next(), time, winners);
                        }
                        return;
                    }
                }

                case "2": {
                    try {
                        controller.needSetMark(getEnteredRow(), getEnteredColumn());
                        break;
                    } catch (OperationNotSupportedException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }

                case "3": {
                    try {
                        controller.needDeleteMark(getEnteredRow(), getEnteredColumn());
                        break;
                    } catch (OperationNotSupportedException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }

                case "0": {
                    return;
                }

                default: {
                    System.out.println("Неверный ввод.");
                }
            }
        }
    }

    private void highScores() throws FileNotFoundException {
        ArrayList<Winner> winners = Minesweeper.getWinners();
        int i = 1;
        for (Winner winner : winners) {
            System.out.println(i + ". " + winner.getName() + " " + millisecondsToDate(winner.getTime()));
            ++i;
        }
    }

    private String millisecondsToDate(long millis) {
        long second = (millis / 1000) % 60;
        long minute = (millis / (1000 * 60)) % 60;
        long hour = (millis / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    private void about() {
        System.out.println("Об игре:)");
    }

    public String levelSelection(String[] levels) {
        System.out.println("Доступны уровни: ");

        for (String level : levels) {
            System.out.println(level);
        }

        while (true) {
            System.out.print("Введите уровень: ");
            String choice = scanner.next();
            for (String level : levels) {
                if (level.equals(choice)) {
                    return choice;
                }
            }
            System.out.println("Неверный ввод.");
        }
    }
}
