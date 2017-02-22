package ru.academits.streltsov.range.main;

import ru.academits.streltsov.range.Range;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Range range = new Range(1, 29);
        Range range1 = new Range(3, 29);
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
            System.out.printf("Границы пересечения: [(%.2f, %.2f)]%n", intersection.getFrom(), intersection.getTo());
        }

        Range[] union = range.getUnion(range1);
        System.out.print("Границы объединения: ");
        System.out.println(Range.toString(union));


        Range[] difference = range.getDifference(range1);
        if (difference.length == 0) {
            System.out.println("Разность равна нулю.");
        } else {
            System.out.print("Границы разности: ");
            System.out.println(Range.toString(difference));
        }
    }
}
