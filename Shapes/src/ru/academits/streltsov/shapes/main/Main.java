package ru.academits.streltsov.shapes.main;


import ru.academits.streltsov.shapes.*;

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = {new Square(10), new Triangle(0, 0, 5, 10, 10, 0),
                new Rectangle(10, 5), new Circle(2), new Circle(10)};

        System.out.println("Фигура с максимальной площадью: " + FindMaxArea.findMaxArea(shapes));
        System.out.println("Фигура со вторым по величине периметром: " + FindSecondMaxPerimeter.findSecondMaxPerimeter(shapes));
    }
}
