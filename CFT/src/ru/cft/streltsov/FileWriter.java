package ru.cft.streltsov;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileWriter {
    public static <T> void writeList(ArrayList<T> list, ProgramArguments programArguments) {
        try (PrintWriter printWriter = new PrintWriter(programArguments.getOutputFileName())) {
            for (T e : list) {
                printWriter.println(e);
            }
        } catch (FileNotFoundException e) {
            System.out.print("Выходной файл не найден.");
        }
    }
}
