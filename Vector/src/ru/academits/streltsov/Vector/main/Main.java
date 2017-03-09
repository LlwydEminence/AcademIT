package ru.academits.streltsov.Vector.main;

import ru.academits.streltsov.Vector.Vector;

public class Main {
    public static void main(String[] args) {
        Vector vector1 = new Vector(10);
        Vector vector2 = new Vector(new double[]{1, 2, 3, 4, 5, 6, 7});
        Vector vector3 = new Vector(10, new double[]{1, 2, 3, 4, 5, 6, 7});

        System.out.println("Вектор1: " + vector1);
        System.out.println("Вектор2: " + vector2);
        System.out.println("Вектор3: " + vector3);

        vector1.add(vector2);
        Vector vector4 = new Vector(vector1);
        System.out.println("Вектор4: " + vector4);

        if (vector1.equals(vector4)) {
            System.out.println("Вектор1 и вектор4 равны.");
        }

        vector2.deduct(vector3);
        System.out.printf("Вектор2: %s%n", vector2);

        System.out.println("Длина вектора2: " + vector2.calculateLength());
        System.out.println("Сумма вектора 1 и 4: " + Vector.sum(vector1, vector4));
        System.out.println("Разность вектора 1 и 4: " + Vector.difference(vector1, vector4));
        System.out.println("Скалярное произведение вектора 1 и 4: " + Vector.composition(vector1, vector4));

    }
}
