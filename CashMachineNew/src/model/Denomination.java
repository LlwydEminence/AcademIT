package model;

public enum Denomination {
    TEN(10, 0),
    FIFTY(50, 1),
    ONE_HUNDRED(100, 2),
    FIVE_HUNDRED(500, 3),
    ONE_THOUSAND(1000, 4),
    FIVE_THOUSAND(5000, 5);

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
