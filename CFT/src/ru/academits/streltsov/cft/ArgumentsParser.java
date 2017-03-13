package ru.academits.streltsov.cft;

public class ArgumentsParser {
    public static ProgramArguments parse(String[] args) {

        if (args.length < 4) {
            throw new IllegalArgumentException("В программе должно быть четыре аргумента.");
        }

        if ((!args[2].equals("-i") && !args[2].equals("-s")) && (!args[3].equals("-a") && !args[3].equals("-d"))) {
            System.out.println(0);
            throw new IllegalArgumentException("Аргументы программы должны быть следующие:" + System.lineSeparator() +
                    "1: входной файл;" + System.lineSeparator() + "2: выходной файл;" + System.lineSeparator() +
                    "3: -i или -s - сортировка чисел или строк соответственно;" + System.lineSeparator() +
                    "4: -a или -d - по возрастанию или по убыванию соответственно." + System.lineSeparator());
        }

        ProgramArguments programArguments = new ProgramArguments();
        programArguments.setInputFileName(args[0]);
        programArguments.setOutputFileName(args[1]);
        if (args[2].equals("-i")) {
            programArguments.setDataType(DataType.INTEGER);
        } else {
            programArguments.setDataType(DataType.STRING);
        }
        programArguments.setAscending(args[3].equals("-a"));
        return programArguments;
    }
}
