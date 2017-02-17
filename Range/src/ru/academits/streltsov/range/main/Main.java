package ru.academits.streltsov.range.main;

import ru.academits.streltsov.range.Range;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Range range = new Range(1, 12);
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        System.out.print("Введите число: ");
        double point = scanner.nextDouble();
        System.out.println();
        System.out.println("Длина интервала: " + range.calculateLength());
        if (range.isInside(point)) {
            System.out.print("Число принадлежит интервалу.");
        } else {
            System.out.print("Число не принадлежит интервалу.");
        }
    }
}
