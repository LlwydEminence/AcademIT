package ru.academits.streltsov.minesweeper.model;

public class Cell {
    private static final int MINE = -1;

    private int value;
    private boolean isOpened;
    private boolean isMarked;
    private boolean isQuestioned;

    public int getValue() {
        return value;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public boolean isQuestioned() {return isQuestioned; }

    void mark() {
        isMarked = true;
    }

    void deleteMark() {
        isMarked = false;
    }

    void question() {
        isQuestioned = true;
    }

    void deleteQuestion() {
        isQuestioned = false;
    }

    void open() {
        isOpened = true;
    }

    void increaseValue() {
        ++value;
    }

    void decreaseValue() {
        --value;
    }

    public boolean isMine() {
        return value == MINE;
    }

    boolean isNoMineNear() {
        return value == 0;
    }

    void putMine() {
        value = MINE;
    }
}
