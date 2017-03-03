package ru.academits.streltsov.shapes;

import java.util.Comparator;

public class ShapeAreaComparator implements Comparator<Shape> {
    public int compare(Shape shape1, Shape shape2) {
        if (shape1.getArea() == shape2.getArea()) {
            return 0;
        } else if (shape1.getArea() > shape2.getArea()) {
            return 1;
        } else {
            return -1;
        }
    }
}
