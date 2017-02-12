package ru.academits.streltsov.range1;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Range range = new Range(1, 10);
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        System.out.print("Введите число: ");
        double point = scanner.nextDouble();
        System.out.println();
        System.out.println("Длина интервала: " + range.calculateIntervalLength());
        if (range.isInside(point)) {
            System.out.print("Число принадлежит интервалу.");
        } else {
            System.out.print("Число не принадлежит интервалу.");
        }

    }
}
