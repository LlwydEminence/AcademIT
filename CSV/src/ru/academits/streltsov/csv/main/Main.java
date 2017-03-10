package ru.academits.streltsov.csv.main;

import ru.academits.streltsov.csv.CSV;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        CSV.createHTMLFromCSVFile("in.txt", "out.html");
    }
}
