package ru.academits.streltsov.cashmachine;

public enum Denomination {
    TEN(10, 1),
    FIFTY(50, 2),
    ONE_HUNDRED(100, 3),
    FIVE_HUNDRED(500, 4),
    ONE_THOUSAND(1000, 5),
    FIVE_THOUSAND(5000, 6);

    private int value;
    private int index;

    Denomination(int value, int index) {
        this.value = value;
        this.index = index;
    }

    int getValue() {
        return value;
    }

    int getIndex() {
        return index;
    }
}
