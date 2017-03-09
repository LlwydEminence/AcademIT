package ru.academits.streltsov.shapes;

import java.util.Comparator;

public class ShapePerimeterComparator implements Comparator<Shape> {
    public int compare(Shape shape1, Shape shape2) {
        return Double.compare(shape1.getPerimeter(), shape2.getPerimeter());
    }
}
