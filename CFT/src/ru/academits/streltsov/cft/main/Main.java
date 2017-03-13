package ru.academits.streltsov.cft.main;

import ru.academits.streltsov.cft.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        try {
            ParsingResults parsingResults = ArgumentsParser.parse(args);
            if (parsingResults.getDataType() == ParsingResults.mode.String) {
                ArrayList<String> list = FileReader.readStringInList(parsingResults);
                if (parsingResults.isAscendingSort()) {
                    InsertionSort.sort(list, new StringComparator());
                } else {
                    InsertionSort.sort(list, new StringComparator().reversed());
                }
                FileWriter.writeList(list, parsingResults);
            } else {
                ArrayList<Integer> list = FileReader.readIntegerInList(parsingResults);
                if (parsingResults.isAscendingSort()) {
                    InsertionSort.sort(list, new IntegerComparator());
                } else {
                    InsertionSort.sort(list, new IntegerComparator().reversed());
                }
                FileWriter.writeList(list, parsingResults);
            }
        } catch (IllegalArgumentException | FileNotFoundException e) {
            System.out.print(e.getMessage());
        }

    }
}
