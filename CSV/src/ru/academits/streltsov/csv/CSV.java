package ru.academits.streltsov.csv;

import java.util.ArrayList;

public class CSV {
    private static final char COMMA = ',';
    private static final char QUOTE = '"';

    public static void convertCSVLineToHTMLTable(ArrayList<String> list, int listIndex, String csvLine) {
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
                    currentString.append(chars[i]);
                    currentString.append(System.lineSeparator());
                    isLineSeparator = true;
                } else {
                    currentString.append(chars[i]);
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
                    currentString.append(chars[i]);
                    currentString.append("</td>");
                } else {
                    currentString.append(chars[i]);
                }
            }
        }

        if (!isLineSeparator) {
            currentString.append("</tr>");
        }

        list.add(currentString.toString());
    }
}
