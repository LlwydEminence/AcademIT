package ru.academits.streltsov.csv;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class CSV {
    private static final char COMMA = ',';
    private static final char QUOTE = '"';

    private static void convertCSVLineToHTMLTable(ArrayList<String> list, int listIndex, String csvLine) {
        StringBuilder currentString = new StringBuilder();
        boolean inQuotes = false;

        if (listIndex == 0 || list.get(listIndex - 1).endsWith("</tr>")) {
            currentString.append("<tr><td>");
        } else {
            inQuotes = true;
        }

        boolean isLineSeparator = false;
        char[] chars = csvLine.toCharArray();

        for (int i = 0; i < chars.length; ++i) {
            if (inQuotes) {
                if (chars[i] == QUOTE && i == chars.length - 1) {
                    currentString.append("</td>");
                    inQuotes = false;
                } else if (chars[i] == QUOTE) {
                    if (chars[i + 1] == QUOTE) {
                        currentString.append(QUOTE);
                        ++i;
                    } else {
                        inQuotes = false;
                    }
                } else if (i == chars.length - 1) {
                    if (chars[i] == '<') {
                        currentString.append("&lt");
                    } else if (chars[i] == '>') {
                        currentString.append("&gt");
                    } else if (chars[i] == '&') {
                        currentString.append("&amp");
                    } else {
                        currentString.append(chars[i]);
                    }
                    currentString.append("<br>");
                    isLineSeparator = true;
                } else {
                    if (chars[i] == '<') {
                        currentString.append("&lt");
                    } else if (chars[i] == '>') {
                        currentString.append("&gt");
                    } else if (chars[i] == '&') {
                        currentString.append("&amp");
                    } else {
                        currentString.append(chars[i]);
                    }
                }
            } else {
                if (chars[i] == QUOTE && i == chars.length - 1) {
                    isLineSeparator = true;
                } else if (chars[i] == COMMA && i == chars.length - 1) {
                    currentString.append("</td><td></td>");
                } else if (chars[i] == QUOTE) {
                    inQuotes = true;
                } else if (chars[i] == COMMA) {
                    currentString.append("</td><td>");
                } else if (i == chars.length - 1) {
                    if (chars[i] == '<') {
                        currentString.append("&lt");
                    } else if (chars[i] == '>') {
                        currentString.append("&gt");
                    } else if (chars[i] == '&') {
                        currentString.append("&amp");
                    } else {
                        currentString.append(chars[i]);
                    }
                    currentString.append("</td>");
                } else {
                    if (chars[i] == '<') {
                        currentString.append("&lt");
                    } else if (chars[i] == '>') {
                        currentString.append("&gt");
                    } else if (chars[i] == '&') {
                        currentString.append("&amp");
                    } else {
                        currentString.append(chars[i]);
                    }
                }
            }
        }

        if (!isLineSeparator) {
            currentString.append("</tr>");
        }

        list.add(currentString.toString());
    }

    public static void createHTMLFromCSVFile(String inputFileName, String outputFileName) throws FileNotFoundException {
        try(Scanner scanner = new Scanner(new FileInputStream(inputFileName));
            PrintWriter printWriter = new PrintWriter(outputFileName)) {
            printWriter.print("<html><head><title>Файл CSV в формате HTML</title><meta charset=\"utf-8\"></head><body><table border=\"1\">");

            ArrayList<String> list = new ArrayList<>();
            int index = 0;

            while (scanner.hasNext()) {
                convertCSVLineToHTMLTable(list, index, scanner.nextLine());
                ++index;
            }

            for (String e : list) {
                printWriter.print(e);
            }

            printWriter.print("</table></body></html>");
        }
    }
}
