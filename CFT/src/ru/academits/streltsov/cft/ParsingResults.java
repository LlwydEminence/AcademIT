package ru.academits.streltsov.cft;

public class ParsingResults {
    public enum mode {
        String, Integer
    }

    private String inputFileName;
    private String outputFileName;
    private mode dataType;
    private boolean isAscending;

    ParsingResults(String[] args) {
        inputFileName = args[0];
        outputFileName = args[1];
        if (args[2].equals("-s")) {
            dataType = mode.String;
        } else {
            dataType = mode.Integer;
        }

        isAscending = args[3].equals("-a");
    }

    String getInputFileName() {
        return inputFileName;
    }

    String getOutputFileName() {
        return outputFileName;
    }

    public  mode getDataType() {
        return dataType;
    }

    public boolean isAscendingSort() {
        return isAscending;
    }
}
