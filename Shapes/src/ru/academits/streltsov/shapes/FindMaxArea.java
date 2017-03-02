package ru.academits.streltsov.shapes;

public class FindMaxArea {
    public static Shape findMaxArea(Shape[] shapes) {
        Shape max = shapes[0];
        for (int i = 1; i < shapes.length; ++i) {
            if (shapes[i].getArea() > max.getArea()) {
                max = shapes[i];
            }
        }
        return max;
    }
}
