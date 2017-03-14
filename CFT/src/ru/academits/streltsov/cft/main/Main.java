package ru.academits.streltsov.cft.main;

import ru.academits.streltsov.cft.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        try {
            ProgramArguments programArguments = ArgumentsParser.parse(args);
            if (programArguments.getDataType() == DataType.STRING) {
                ArrayList<String> list = FileReader.readStringInList(programArguments);
                if (programArguments.isAscendingSort()) {
                    InsertionSort.sort(list, new StringComparator());
                } else {
                    InsertionSort.sort(list, new StringComparator().reversed());
                }
                FileWriter.writeList(list, programArguments);
            } else {
                ArrayList<Integer> list = FileReader.readIntegerInList(programArguments);
                if (programArguments.isAscendingSort()) {
                    InsertionSort.sort(list, new IntegerComparator());
                } else {
                    InsertionSort.sort(list, new IntegerComparator().reversed());
                }
                FileWriter.writeList(list, programArguments);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.print("Аргументы программы должны быть следующие:" + System.lineSeparator() +
                    "1: входной файл;" + System.lineSeparator() + "2: выходной файл;" + System.lineSeparator() +
                    "3: -i или -s - сортировка чисел или строк соответственно;" + System.lineSeparator() +
                    "4: -a или -d - по возрастанию или по убыванию соответственно.");
        } catch (FileNotFoundException e) {
            System.out.print(e.getMessage());
        }

    }
}
