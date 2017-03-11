package ru.academits.streltsov.cft;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileWriter {
    public static <T> void writeList(ArrayList<T> list) {
        try(PrintWriter printWriter = new PrintWriter(ParsingResults.getOutputFileName())) {
                for (T e : list) {
                    printWriter.println(e);
                }
        } catch (FileNotFoundException e) {
            System.out.print("Выходной файл не найден.");
        }
    }
}
