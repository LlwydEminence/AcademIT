package model;

class Cash {
    private Denomination denomination;
    private int number;

    Cash(Denomination denomination, int number) {
        this.denomination = denomination;
        this.number = number;
    }

    int getValue() {
        return denomination.getValue();
    }

    int getNumber() {
        return number;
    }

    void increaseNumber(int addition) {
        number += addition;
    }

    void decreaseNumber(int reduction) {
        number -= reduction;
    }
}
