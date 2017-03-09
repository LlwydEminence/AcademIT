package ru.academits.streltsov.csv.main;

import ru.academits.streltsov.csv.CSV;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        try(Scanner scanner = new Scanner(new FileInputStream("in.txt"));
            PrintWriter printWriter = new PrintWriter("out.html")) {

            printWriter.print("<table>");
            ArrayList<String> list = new ArrayList<>();
            int listIndex = 0;
            while (scanner.hasNext()) {
                CSV.convertCSVLineToHTMLTable(list, listIndex, scanner.nextLine());
                ++listIndex;
            }
            for (String e : list) {
                printWriter.print(e);
            }
            printWriter.print("</table>");
        }
    }
}
