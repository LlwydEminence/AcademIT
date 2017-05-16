package ru.academits.streltsov.minesweeper.model;

public class Winner {
    private String name;
    private long time;

    Winner(String name, long time) {
        this.name = name;
        this.time = time;
    }

    @Override
    public String toString() {
        return name + " " + time;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }
}
