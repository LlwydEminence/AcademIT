package ru.academits.streltsov.range.main;

import ru.academits.streltsov.range.Range;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Range range = new Range(2, 16);
        Range range1 = new Range(1, 19);
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
            System.out.println("Интервалы не пересекаются.");
        } else {
            System.out.printf("Границы пересечения: (%.2f, %.2f)%n", intersection.getFrom(), intersection.getTo());
        }

        Range[] union = range.getUnion(range1);
        if (union.length == 2) {
            System.out.printf("Границы объединения: (%.2f, %.2f), (%.2f, %.2f)%n", union[0].getFrom(), union[0].getTo(), union[1].getFrom(), union[1].getTo());
        } else {
            System.out.printf("Границы объединения: (%.2f, %.2f)%n", union[0].getFrom(), union[0].getTo());
        }

        Range[] difference = range.getDifference(range1);
        if (difference.length == 0) {
            System.out.println("Разность равна нулю.");
        } else if (difference.length == 1) {
            System.out.printf("Границы разности: (%.2f, %.2f)", difference[0].getFrom(), difference[0].getTo());
        } else {
            System.out.printf("Границы разности: (%.2f, %.2f), (%.2f, %.2f)", difference[0].getFrom(), difference[0].getTo(), difference[1].getFrom(), difference[1].getTo());
        }
    }
}
