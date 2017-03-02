package ru.academits.streltsov.shapes;

public class FindSecondMaxPerimeter {
    public static Shape findSecondMaxPerimeter(Shape[] shapes) {
        Shape max = shapes[0];
        int maxIndex = 0;
        for (int i = 1; i < shapes.length; ++i) {
            if (shapes[i].getPerimeter() > max.getPerimeter()) {
                max = shapes[i];
                maxIndex = i;
            }
        }

        Shape secondMax = shapes[0];
        for (int i = 1; i < shapes.length; ++i) {
            if (i != maxIndex && shapes[i].getPerimeter() > secondMax.getPerimeter()) {
                secondMax = shapes[i];
            }
        }
        return secondMax;
    }
}
