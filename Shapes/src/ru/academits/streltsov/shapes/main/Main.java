package ru.academits.streltsov.shapes.main;


import ru.academits.streltsov.shapes.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        ArrayList<Shape> list = new ArrayList<>(Arrays.asList(new Square(10), new Triangle(0, 0, 5, 10, 10, 0),
                new Rectangle(10, 5), new Circle(20), new Circle(100)));

        System.out.println("Фигура с максимальной площадью: " + Collections.max(list, new ShapeAreaComparator()));
        list.sort(new ShapePerimeterComparator());
        System.out.println("Фигура со вторым по величине периметром: " + list.get(list.size() - 2));
    }
}
