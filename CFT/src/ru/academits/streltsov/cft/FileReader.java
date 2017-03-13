package ru.academits.streltsov.cft;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
    public static ArrayList<String> readStringInList(ProgramArguments programArguments) throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileInputStream(programArguments.getInputFileName()))) {
            while (scanner.hasNext()) {
                list.add(scanner.nextLine());
            }
        }

        return list;
    }

    public static ArrayList<Integer> readIntegerInList(ProgramArguments programArguments) throws FileNotFoundException {
        ArrayList<Integer> list = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileInputStream(programArguments.getInputFileName()))) {
            while (scanner.hasNext()) {
                list.add(scanner.nextInt());
            }
        }

        return list;
    }
}
