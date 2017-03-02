package ru.academits.streltsov.shapes;

import java.util.Objects;

public class Rectangle implements Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public double getPerimeter() {
        return (width + height) * 2;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (otherObject == null || this.getClass() != otherObject.getClass()) {
            return false;
        }

        Rectangle other = (Rectangle) otherObject;

        return this.width == other.width && this.height == other.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[width = " + width + ", height = " + height + "]";
    }
}
