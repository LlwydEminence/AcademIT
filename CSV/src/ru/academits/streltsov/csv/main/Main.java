package ru.academits.streltsov.csv.main;

import ru.academits.streltsov.csv.CSV;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            CSV.createHTMLFromCSVFile(args[0], args[1]);
        } catch (FileNotFoundException e) {
            System.out.print("Файл " + args[0] + " не найден.");
        }

    }
}
