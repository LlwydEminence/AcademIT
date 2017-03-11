package ru.academits.streltsov.cft.main;

import ru.academits.streltsov.cft.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        try {
            ArgumentsParser.parse(args);
            if (ParsingResults.getDataType().equals("String")) {
                ArrayList<String> list = FileReader.readStringInList();
                if (ParsingResults.isAscendingSort()) {
                    InsertionSort.sort(list, new StringComparator());
                } else {
                    InsertionSort.sort(list, new StringComparator().reversed());
                }
                FileWriter.writeList(list);
            } else {
                ArrayList<Integer> list = FileReader.readIntegerInList();
                if (ParsingResults.isAscendingSort()) {
                    InsertionSort.sort(list, new IntegerComparator());
                } else {
                    InsertionSort.sort(list, new IntegerComparator().reversed());
                }
                FileWriter.writeList(list);
            }
        } catch (IllegalArgumentException | FileNotFoundException e) {
            System.out.print(e.getMessage());
        }

    }
}
