package ru.academits.streltsov.cft;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class TestTask {

    public static void insertInt(int[] array, int index, String arg) {
        int requiredIndex = 0;
        int left = 0;
        int right = index - 1;
        int temp = array[index];
        while (left <= right){
            int middle = (left + right) / 2;
            if (arg.equals("a")) {
                if (temp == array[middle] || (temp < array[middle] && (middle == 0 || temp > array[middle - 1]))) {
                    requiredIndex = middle;
                    break;
                } else if (temp < array[middle]) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            } else {
                if (temp == array[middle] || (temp > array[middle] && (middle == 0 || temp < array[middle - 1]))) {
                    requiredIndex = middle;
                    break;
                } else if (temp > array[middle]) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            }
        }
        for (int i = index; i > requiredIndex; --i) {
            array[i] = array[i - 1];
        }
        array[requiredIndex] = temp;
    }

    public static void sortInt(int[] array, String arg) {
        for (int i = 1; i < array.length; ++i) {
            if (arg.equals("a")) {
                if (array[i] < array[i - 1]) {
                    insertInt(array, i, arg);
                }
            } else {
                if (array[i] > array[i - 1]) {
                    insertInt(array, i, arg);
                }
            }
        }
    }

    public static void insertString(String[] strings, int index, String arg) {
        int requiredIndex = 0;
        int left = 0;
        int right = index - 1;
        String temp = strings[index];
        while (left <= right){
            int middle = (left + right) / 2;
            if (arg.equals("a")) {
                if (temp.equals(strings[middle]) || (temp.compareTo(strings[middle]) < 0 && (middle == 0 || temp.compareTo(strings[middle - 1]) > 0))) {
                    requiredIndex = middle;
                    break;
                } else if (temp.compareTo(strings[middle]) < 0) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            } else {
                if (temp.equals(strings[middle]) || (temp.compareTo(strings[middle]) > 0 && (middle == 0 || temp.compareTo(strings[middle - 1]) < 0))) {
                    requiredIndex = middle;
                    break;
                } else if (temp.compareTo(strings[middle]) > 0) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            }
        }
        for (int i = index; i > requiredIndex; --i) {
            strings[i] = strings[i - 1];
        }
        strings[requiredIndex] = temp;
    }

    public static  void sortString(String[] strings, String arg) {
        for (int i = 1; i < strings.length; ++i) {
            if (arg.equals("a")) {
                if (strings[i].compareTo(strings[i-1]) < 0) {
                    insertString(strings, i, arg);
                }
            } else {
                if (strings[i].compareTo(strings[i - 1]) > 0) {
                    insertString(strings, i, arg);
                }
            }

        }
    }

    public static boolean isAllowableFileName(String arg) {
        char[] symbols = arg.toCharArray();
        String validationString = "!@#$%^в„–&*()-_=+/*\\{[}]?:;'\"<>,~";

        if (!arg.endsWith(".txt")) {
            return false;
        }
        for (char c: symbols) {
            if (validationString.indexOf(c) != -1) {
                return false;
            }
        }
        return true;
    }

    final static int ARRAY_SIZE = 100;
    final static int STRING_ARRAY_SIZE = 8;

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length < 4 || !isAllowableFileName(args[0]) || !isAllowableFileName(args[1])) {
            System.out.print("Р”РѕР»Р¶РЅРѕ Р±С‹С‚СЊ С‡РµС‚С‹СЂРµ Р°СЂРіСѓРјРµРЅС‚Р°!\nРџРµСЂРІС‹Рµ РґРІР° Р°СЂРіСѓРјРµРЅС‚Р° РґРѕР»Р¶РЅС‹ Р±С‹С‚СЊ РЅР°Р·РІР°РЅРёСЏРјРё С„Р°Р№Р»РѕРІ СЃ СЂР°СЃС€РёСЂРµРЅРёРµРј .txt!\nР’ РЅР°Р·РІР°РЅРёСЏС… С„Р°Р№Р»РѕРІ С‚РѕР»СЊРєРѕ Р±СѓРєРІС‹ Рё С†РёС„СЂС‹!");
            return;
        }
        switch (args[3]) {
            case "a": {
                if (!args[2].equals("i") && !args[2].equals("s")) {
                    System.out.print("РўСЂРµС‚РёР№ РїР°СЂР°РјРµС‚СЂ РґРѕР»Р¶РµРЅ Р±С‹С‚СЊ \"i\" РёР»Рё \"s\"!");
                    return;
                } else {
                    break;
                }
            }
            case "d": {
                if (!args[2].equals("i") && !args[2].equals("s")) {
                    System.out.print("РўСЂРµС‚РёР№ РїР°СЂР°РјРµС‚СЂ РґРѕР»Р¶РµРЅ Р±С‹С‚СЊ \"i\" РёР»Рё \"s\"!");
                    return;
                } else {
                    break;
                }
            }
            default: {
                if (!args[2].equals("i") && !args[2].equals("s")) {
                    System.out.println("РўСЂРµС‚РёР№ РїР°СЂР°РјРµС‚СЂ РґРѕР»Р¶РµРЅ Р±С‹С‚СЊ \"i\" РёР»Рё \"s\"!");
                }
                System.out.print("Р§РµС‚РІРµСЂС‚С‹Р№ РїР°СЂР°РјРµС‚СЂ РґРѕР»Р¶РµРЅ Р±С‹С‚СЊ \"a\" РёР»Рё \"d\"!");
                return;
            }
        }

        try (PrintWriter printWriter = new PrintWriter(args[0])) {
            if (args[2].equals("i")) {
                Random random = new Random();
                int count = 0;
                while (count < ARRAY_SIZE) {
                    printWriter.println(random.nextInt(ARRAY_SIZE));
                    ++count;
                }
            } else {
                String[] strings = {"Doctor", "Rose", "Mickey", "Jack", "Martha", "Donna", "Amy", "Rory"};
                for (String e : strings) {
                    printWriter.println(e);
                }
            }
        }
        try (PrintWriter printWriter = new PrintWriter(args[1]);
             Scanner scanner = new Scanner(new FileInputStream(args[0]))) {
            if (args[2].equals("i")) {
                int[] array = new int[ARRAY_SIZE];
                int i = 0;
                while (scanner.hasNextInt()) {
                    array[i] = scanner.nextInt();
                    ++i;
                    scanner.nextLine();
                }
                sortInt(array, args[3]);
                for (int e : array) {
                    printWriter.println(e);
                }
            } else {
                String[] strings = new String[STRING_ARRAY_SIZE];
                int i = 0;
                while (scanner.hasNextLine()) {
                    strings[i] = scanner.nextLine();
                    ++i;
                }
                sortString(strings, args[3]);
                for (String e : strings) {
                    printWriter.println(e);
                }
            }
        }
    }
}

