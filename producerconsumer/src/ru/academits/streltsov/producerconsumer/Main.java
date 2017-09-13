package ru.academits.streltsov.producerconsumer;

import ru.academits.streltsov.producerconsumer.manager.AppleOrchard;

public class Main {
    private static final int NUMBER_OF_APPLES = 50;
    private static final int BASKET_CAPACITY = 10;
    private static final int APPLE_PICKERS_NUMBER = 4;
    private static final int THIEVES_NUMBER = 6;

    public static void main(String[] args) {
        new AppleOrchard(NUMBER_OF_APPLES, BASKET_CAPACITY, APPLE_PICKERS_NUMBER, THIEVES_NUMBER);
    }
}
