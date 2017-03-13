package ru.academits.streltsov.cft;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
    public static ArrayList<String> readStringInList(ParsingResults parsingResults) throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileInputStream(parsingResults.getInputFileName()))) {
            while (scanner.hasNext()) {
                list.add(scanner.nextLine());
            }
        }

        return list;
    }

    public static ArrayList<Integer> readIntegerInList(ParsingResults parsingResults) throws FileNotFoundException {
        ArrayList<Integer> list = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileInputStream(parsingResults.getInputFileName()))) {
            while (scanner.hasNext()) {
                list.add(scanner.nextInt());
            }
        }

        return list;
    }
}
