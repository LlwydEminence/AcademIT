package ru.academits.streltsov.shapes;

import java.util.Objects;

public class Square implements Shape {
    private double sideLength;

    public Square(double sideLength) {
        this.sideLength = sideLength;
    }

    public double getSideLength() {
        return this.sideLength;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }

    public double getWidth() {
        return sideLength;
    }

    public double getHeight() {
        return sideLength;
    }

    public double getArea() {
        return sideLength * sideLength;
    }

    public double getPerimeter() {
        return (sideLength + sideLength) * 2;
    }

    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (otherObject == null || this.getClass() != otherObject.getClass()) {
            return false;
        }

        Square other = (Square) otherObject;

        return this.sideLength == other.sideLength;
    }

    public int hashCode() {
        return 37 + (int) sideLength;
    }

    public String toString() {
        return getClass().getSimpleName() + "[sideLength = " + sideLength + "]";
    }
}
