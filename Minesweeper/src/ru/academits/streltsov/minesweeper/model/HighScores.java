package ru.academits.streltsov.minesweeper.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HighScores {
    private static final int HIGH_SCORES_POSITION_NUMBERS = 10;
    private final static String HIGH_SCORES_FOR_BEGINNER_FILE_PATH =
            "Minesweeper/src/ru/academits/streltsov/minesweeper/resources/HighScoresForBeginner.txt";
    private final static String HIGH_SCORES_FOR_AMATEUR_FILE_PATH =
            "Minesweeper/src/ru/academits/streltsov/minesweeper/resources/HighScoresForAmateur.txt";
    private final static String HIGH_SCORES_FOR_EXPERT_FILE_PATH =
            "Minesweeper/src/ru/academits/streltsov/minesweeper/resources/HighScoresForExpert.txt";

    private String filePath;

    public HighScores(String level) {
        switch (level) {
            case Minesweeper.BEGINNER: {
                filePath = HIGH_SCORES_FOR_BEGINNER_FILE_PATH;
                break;
            }

            case Minesweeper.AMATEUR: {
                filePath = HIGH_SCORES_FOR_AMATEUR_FILE_PATH;
                break;
            }

            case Minesweeper.EXPERT: {
                filePath = HIGH_SCORES_FOR_EXPERT_FILE_PATH;
                break;
            }
        }
    }

    void add(String name, long time, ArrayList<Winner> winners) throws FileNotFoundException {
        if (winners.size() >= HIGH_SCORES_POSITION_NUMBERS) {
            winners.remove(winners.size() - 1);
        }

        winners.add(new Winner(name, time));
        winners.sort((o1, o2) -> {
            long o1Time = o1.getTime();
            long o2Time = o2.getTime();
            if (o1Time == o2Time) {
                return 0;
            } else if (o1Time < o2Time) {
                return -1;
            } else {
                return 1;
            }
        });

        try (PrintWriter printWriter =
                     new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath)))) {

            for (Winner winner : winners) {
                printWriter.println(winner);
            }
        }
    }

    public ArrayList<Winner> getData() throws FileNotFoundException {
        ArrayList<Winner> winners = new ArrayList<>();
        try (Scanner scanner =
                     new Scanner(new FileInputStream(filePath))) {
            while (scanner.hasNext()) {
                winners.add(new Winner(scanner.next(), scanner.nextLong()));
            }
        }
        return winners;
    }
}
