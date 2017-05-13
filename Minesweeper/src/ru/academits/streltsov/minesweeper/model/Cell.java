package ru.academits.streltsov.minesweeper.model;

public class Cell {
    private int value;
    private boolean isOpened;
    private boolean isMarked;

    Cell(int value) {
        this.value = value;
        isOpened = false;
        isMarked = false;
    }

    public int getValue() {
        return value;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public boolean isOpened() {
        return isOpened;
    }

    void mark() {
        isMarked = true;
    }

    void deleteMark() {
        isMarked = false;
    }

    void open() {
        isOpened = true;
    }

    void setValue(int value) {
        this.value = value;
    }
}
