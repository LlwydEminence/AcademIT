package ru.academits.streltsov.shapes;

import java.util.Objects;

public class Triangle implements Shape {
    private double x1;
    private double x2;
    private double x3;
    private double y1;
    private double y2;
    private double y3;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getX3() {
        return x3;
    }

    public double getY1() {
        return y1;
    }

    public double getY2() {
        return y2;
    }

    public double getY3() {
        return y3;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public void setX3(double x3) {
        this.x3 = x3;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public void setY3(double y3) {
        this.y3 = y3;
    }


    private static double calculateSideLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    @Override
    public double getWidth() {
        return Math.max(Math.max(x1, x2), x3) - Math.min(Math.min(x1, x2), x3);
    }

    @Override
    public double getHeight() {
        return Math.max(Math.max(y1, y2), y3) - Math.min(Math.min(y1, y2), y3);
    }

    @Override
    public double getPerimeter() {
        return calculateSideLength(x1, y1, x2, y2) + calculateSideLength(x2, y2, x3, y3) + calculateSideLength(x1, y1, x3, y3);
    }

    @Override
    public double getArea() {
        double perimeter = getPerimeter();
        return Math.sqrt(perimeter / 2 * (perimeter / 2 - calculateSideLength(x1, y1, x2, y2)) * (perimeter/ 2 - calculateSideLength(x2, y2, x3, y3)) * (perimeter / 2 - calculateSideLength(x1, y1, x3, y3)));
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (otherObject == null || this.getClass() != otherObject.getClass()) {
            return false;
        }

        Triangle other = (Triangle) otherObject;

        return this.x1 == other.x1 && this.x2 == other.x2 && this.x3 == other.x3 && this.y1 == other.y1 && this.y2 == other.y2 && this.y3 == other.y3;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + (int) x1;
        hash = prime * hash + (int) x2;
        hash = prime * hash + (int) x3;
        hash = prime * hash + (int) y1;
        hash = prime * hash + (int) y2;
        hash = prime * hash + (int) y3;
        return hash;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[(" + x1 + ", " + y1 + "), "+ "(" + x2 + ", " + y2 + "), "+ "(" + x3 + ", " + y3 + ")]";
    }
}
