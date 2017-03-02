package ru.academits.streltsov.range.main;

import ru.academits.streltsov.range.Range;
import ru.academits.streltsov.range.exceptions.InvalidIntervalException;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Range range = new Range(1, 0);
            Range range1 = new Range(3, 21);
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
                System.out.print("Границы пересечения: ");
                System.out.println(intersection.toString());
            }

            Range[] union = range.getUnion(range1);
            System.out.print("Границы объединения: ");
            System.out.println(Arrays.toString(union));


            Range[] difference = range.getDifference(range1);
            if (difference.length == 0) {
                System.out.println("Разность равна нулю.");
            } else {
                System.out.print("Границы разности: ");
                System.out.println(Arrays.toString(range.getDifference(range1)));
            }
        } catch (InvalidIntervalException e) {
            System.out.print("Конец должен быть больше начала!");
        }
    }
}
