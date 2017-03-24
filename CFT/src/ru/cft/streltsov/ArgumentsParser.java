package ru.cft.streltsov;

public class ArgumentsParser {
    public static ProgramArguments parse(String[] args) {

        if (args.length < 4) {
            throw new IllegalArgumentException("В программе должно быть четыре аргумента.");
        }

        if (!args[2].equals("-i") && !args[2].equals("-s")) {
            throw new IllegalArgumentException("Ошибка в третьем аргументе.");
        } else if (!args[3].equals("-a") && !args[3].equals("-d")) {
            throw new IllegalArgumentException("Ошибка в четвертом аргументе.");
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
