package model;

public class Cash {
    private Denomination denomination;
    private int number;

    Cash(Denomination denomination, int number) {
        this.denomination = denomination;
        this.number = number;
    }

    public int getValue() {
        return denomination.getValue();
    }

    Denomination getDenomination() {
        return denomination;
    }

    public int getNumber() {
        return number;
    }

    void increaseNumber(int addition) {
        number += addition;
    }

    void decreaseNumber(int reduction) {
        number -= reduction;
    }
}
