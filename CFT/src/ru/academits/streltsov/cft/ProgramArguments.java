package ru.academits.streltsov.cft;

public class ProgramArguments {
    private String inputFileName;
    private String outputFileName;
    private DataType dataType;
    private boolean isAscending;

    String getInputFileName() {
        return inputFileName;
    }

    String getOutputFileName() {
        return outputFileName;
    }

    public  DataType getDataType() {
        return dataType;
    }

    public boolean isAscendingSort() {
        return isAscending;
    }

    void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    void setAscending (boolean isAscending) {
        this.isAscending = isAscending;
    }
}
