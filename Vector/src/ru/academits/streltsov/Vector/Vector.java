package ru.academits.streltsov.Vector;

import java.util.Arrays;
import java.util.Map;

public class Vector {
    private int size;
    private double[] coordinates;
    private static double EPSILON = 0.0001;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.size = size;
            coordinates = new double[size];
            Arrays.fill(coordinates, 0);
        }
    }

    public Vector(Vector vector) {
        this.size = vector.size;
        coordinates = vector.coordinates;
    }

    public Vector(double[] coordinates) {
        this.coordinates = coordinates;
        this.size = coordinates.length;
    }

    public Vector(int size, double[] coordinates) {
        if (size <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.coordinates = Arrays.copyOf(coordinates, size);
            this.size = size;
        }
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(coordinates[0]);

        if (size > 1) {
            for (int i = 1; i < size; ++i) {
                stringBuilder.append(", ");
                stringBuilder.append(coordinates[i]);
            }
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void reCreateArray(double[] coordinates, int size) {
        this.coordinates = Arrays.copyOf(coordinates, size);
    }

    public void add(Vector vector) {
        if (this.size < vector.size) {
            this.size = vector.size;
            reCreateArray(coordinates, size);
        }

        for (int i = 0; i < vector.size; ++i) {
            coordinates[i] += vector.coordinates[i];
        }
    }

    public void deduct(Vector vector) {
        if (this.size < vector.size) {
            this.size = vector.size;
            reCreateArray(coordinates, size);
        }

        for (int i = 0; i < vector.size; ++i) {
            coordinates[i] -= vector.coordinates[i];
        }
    }

    public void multiply(double scalar) {
        for (int i = 0; i < size; ++i) {
            coordinates[i] *= scalar;
        }
    }

    public void invert() {
        for (int i = 0; i < size; ++i) {
            coordinates[i] *= -1;
        }
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
        if (size == other.size) {
            for (int i = 0; i < size; ++i) {
                if (Math.abs((coordinates[i] - other.coordinates[i])) >= EPSILON) {
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
        return 7 * size + 11 * Arrays.hashCode(coordinates);
    }

    public static Vector sum(Vector vector1, Vector vector2) {
        if (vector1.size < vector2.size) {
            vector1.size = vector2.size;
            vector1.reCreateArray(vector1.coordinates, vector1.size);
        } else if (vector1.size > vector2.size) {
            vector2.size = vector1.size;
            vector2.reCreateArray(vector2.coordinates, vector2.size);
        }

        Vector vector = new Vector(vector1.size);
        for (int i = 0; i < vector.size; ++i) {
            vector.coordinates[i] = vector1.coordinates[i] + vector2.coordinates[i];
        }

        return vector;
    }

    public static Vector difference(Vector vector1, Vector vector2) {
        if (vector1.size < vector2.size) {
            vector1.size = vector2.size;
            vector1.reCreateArray(vector1.coordinates, vector1.size);
        } else if (vector1.size > vector2.size) {
            vector2.size = vector1.size;
            vector2.reCreateArray(vector2.coordinates, vector2.size);
        }

        Vector vector = new Vector(vector1.size);
        for (int i = 0; i < vector.size; ++i) {
            vector.coordinates[i] = vector1.coordinates[i] - vector2.coordinates[i];
        }

        return vector;
    }

    public static Vector composition(Vector vector1, Vector vector2) {
        if (vector1.size < vector2.size) {
            vector1.size = vector2.size;
            vector1.reCreateArray(vector1.coordinates, vector1.size);
        } else if (vector1.size > vector2.size) {
            vector2.size = vector1.size;
            vector2.reCreateArray(vector2.coordinates, vector2.size);
        }

        Vector vector = new Vector(vector1.size);
        for (int i = 0; i < vector.size; ++i) {
            vector.coordinates[i] = vector1.coordinates[i] * vector2.coordinates[i];
        }

        return vector;
    }
}
