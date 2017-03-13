package ru.academits.streltsov.csv.main;

import ru.academits.streltsov.csv.CSV;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            CSV.createHTMLFromCSVFile(args);
        } catch (IllegalArgumentException | FileNotFoundException e) {
            System.out.print(e.getMessage());
        }

    }
}
