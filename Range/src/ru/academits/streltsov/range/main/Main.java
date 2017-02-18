package ru.academits.streltsov.range.main;

import ru.academits.streltsov.range.Range;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Range range = new Range(10, 16);
        Range range1 = new Range(1, 16);
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        /*System.out.print("Введите число: ");
        double point = scanner.nextDouble();
        System.out.println();
        System.out.println("Длина интервала: " + range.calculateLength());
        if (range.isInside(point)) {
            System.out.print("Число принадлежит интервалу.");
        } else {
            System.out.print("Число не принадлежит интервалу.");
        }*/

        Range intersection = range.getIntersection(range1);
        if (intersection == null) {
            System.out.println("Интервалы не пересекаются!");
            System.out.printf("Границы объединения и разности: (%.2f, %.2f) и (%.2f, %.2f)", range.getFrom(), range.getTo(), range1.getFrom(), range1.getTo());
        } else {
            System.out.printf("Границы пересечения: (%.2f, %.2f)%n", intersection.getFrom(), intersection.getTo());
            Range merger = range.getMerger(range1);
            System.out.printf("Границы объединения: (%.2f, %.2f)%n", merger.getFrom(), merger.getTo());
            Range[] difference = range.getDifference(range1);
            if (difference == null) {
                System.out.print("Разность интервалов равна нулю.");
            } else if (difference[1] == null) {
                System.out.printf("Границы разности: (%.2f, %.2f)", difference[0].getFrom(), difference[0].getTo());
            } else {
                System.out.printf("Границы разности: (%.2f, %.2f), (%.2f, %.2f)", difference[0].getFrom(), difference[0].getTo(), difference[1].getFrom(), difference[1].getTo());
            }
        }
    }
}
