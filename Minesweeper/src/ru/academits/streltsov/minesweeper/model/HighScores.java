package ru.academits.streltsov.minesweeper.model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class HighScores {
    private static final int HIGH_SCORES_POSITION_NUMBERS = 10;
    private final static String HIGH_SCORES_FOR_BEGINNER_FILE_PATH =
            "Minesweeper/src/ru/academits/streltsov/minesweeper/resources/HighScoresForBeginner.txt";
    private final static String HIGH_SCORES_FOR_AMATEUR_FILE_PATH =
            "Minesweeper/src/ru/academits/streltsov/minesweeper/resources/HighScoresForAmateur.txt";
    private final static String HIGH_SCORES_FOR_EXPERT_FILE_PATH =
            "Minesweeper/src/ru/academits/streltsov/minesweeper/resources/HighScoresForExpert.txt";
    private final static HashMap<String, String> hashMap = new HashMap<>();
    private String filePath;

    static {
        hashMap.put(Minesweeper.BEGINNER, HIGH_SCORES_FOR_BEGINNER_FILE_PATH);
        hashMap.put(Minesweeper.AMATEUR, HIGH_SCORES_FOR_AMATEUR_FILE_PATH);
        hashMap.put(Minesweeper.EXPERT, HIGH_SCORES_FOR_EXPERT_FILE_PATH);
    }

    public HighScores(String level) {
        filePath = hashMap.get(level);
    }

    void add(String name, long time, ArrayList<Winner> winners) throws FileNotFoundException {
        if (winners.size() >= HIGH_SCORES_POSITION_NUMBERS) {
            winners.remove(winners.size() - 1);
        }

        winners.add(new Winner(name, time));
        winners.sort((o1, o2) -> {
            long o1Time = o1.getTime();
            long o2Time = o2.getTime();
            return Long.compare(o1Time, o2Time);
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
