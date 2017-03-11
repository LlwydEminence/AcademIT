package ru.academits.streltsov.cft;

public class ParsingResults {
    private static String inputFileName;
    private static String outputFileName;
    private static String dataType;
    private static boolean isAscending;


    static void setInputFileName(String string) {
        inputFileName = string;
    }

    static void setOutputFileName(String string) {
        outputFileName = string;
    }

    static void setDataType(String string) {
        if (string.equals("-i")) {
            dataType = "Integer";
        } else {
            dataType = "String";
        }
    }

    static void setSortDirection(String string) {
        isAscending = string.equals("-a");
    }

    static String getInputFileName() {
        return inputFileName;
    }

    static String getOutputFileName() {
        return outputFileName;
    }

    public static String getDataType() {
        return dataType;
    }

    public static boolean isAscendingSort() {
        return isAscending;
    }
}
