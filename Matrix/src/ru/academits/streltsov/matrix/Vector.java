package ru.academits.streltsov.matrix;

import java.util.Arrays;

public class Vector {
    private double[] coordinates;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер вектора не может быть меньше 1!");
        } else {
            coordinates = new double[size];
        }
    }

    public Vector(Vector vector) {
        coordinates = Arrays.copyOf(vector.coordinates, vector.coordinates.length);
    }

    public Vector(double[] coordinates) {
        if (coordinates.length == 0) {
            throw new IllegalArgumentException("Массив не должен быть нулевой длины!");
        }

        this.coordinates = Arrays.copyOf(coordinates, coordinates.length);

    }

    public Vector(int size, double[] coordinates) {
        if (size <= 0 || coordinates.length == 0) {
            throw new IllegalArgumentException("Длина массива должна быть больше нуля, размерность ветора не меньше 1!");
        } else {
            this.coordinates = Arrays.copyOf(coordinates, size);
        }
    }

    int getSize() {
        return coordinates.length;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(coordinates[0]);

        for (int i = 1; i < getSize(); ++i) {
            stringBuilder.append(", ");
            stringBuilder.append(coordinates[i]);
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    void reCreateArray(double[] coordinates, int size) {
        this.coordinates = Arrays.copyOf(coordinates, size);
    }

    public void add(Vector vector) {
        if (getSize() < vector.getSize()) {
            reCreateArray(coordinates, vector.getSize());
        }

        for (int i = 0; i < vector.getSize(); ++i) {
            coordinates[i] += vector.coordinates[i];
        }
    }

    public void deduct(Vector vector) {
        if (getSize() < vector.getSize()) {
            reCreateArray(coordinates, vector.getSize());
        }

        for (int i = 0; i < vector.getSize(); ++i) {
            coordinates[i] -= vector.coordinates[i];
        }
    }

    public void multiply(double scalar) {
        for (int i = 0; i < getSize(); ++i) {
            coordinates[i] *= scalar;
        }
    }

    public void invert() {
        this.multiply(-1);
    }

    public double calculateLength() {
        double sum = 0;
        for (double e : coordinates) {
            sum += Math.pow(e, 2);
        }
        return Math.sqrt(sum);
    }

    public double getCoordinate(int index) {
        return coordinates[index];
    }

    public void setCoordinate(int index, double coordinate) {
        coordinates[index] = coordinate;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }

        Vector other = (Vector) otherObject;
        if (getSize() == other.getSize()) {
            for (int i = 0; i < getSize(); ++i) {
                if (coordinates[i] != other.coordinates[i]) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 7 * getSize() + 11 * Arrays.hashCode(coordinates);
    }

    public static Vector sum(Vector vector1, Vector vector2) {
        Vector vector = new Vector(vector1);
        vector.add(vector2);
        return vector;
    }

    public static Vector difference(Vector vector1, Vector vector2) {
        Vector vector = new Vector(vector1);
        vector.deduct(vector2);
        return vector;
    }

    public static double getScalarProduct(Vector vector1, Vector vector2) {
        double scalarProduct = 0;
        int minSize = Math.min(vector1.getSize(), vector2.getSize());
        for (int i = 0; i < minSize; ++i) {
            scalarProduct += vector1.getCoordinate(i) * vector2.getCoordinate(i);
        }
        return scalarProduct;
    }
}
