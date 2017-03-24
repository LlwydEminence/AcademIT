package ru.academits.streltsov.matrix;

import java.util.Arrays;
import java.util.Collections;

public class Matrix {
    private Vector[] vectors;

    public Matrix(int n, int m) {
        fillVectors(n, m);
    }

    public Matrix(Matrix matrix) {
        int rowsNumber = matrix.getRowsNumber();
        int columnsNumber = matrix.getColumnsNumber();
        fillVectors(rowsNumber, columnsNumber);

        for (int i = 0; i < rowsNumber; ++i) {
            for (int j = 0; j < columnsNumber; ++j) {
                setElement(i, j, matrix.getElement(i, j));
            }
        }
    }

    public Matrix(double[][] elements) {
        int rowsNumber = elements.length;
        int columnsNumber = elements[0].length;
        fillVectors(rowsNumber, columnsNumber);

        for (int i = 0; i < rowsNumber; ++i) {
            for (int j = 0; j < columnsNumber; ++j) {
                setElement(i, j, elements[i][j]);
            }
        }
    }

    public Matrix(Vector[] vectors) {
        int rowsNumber = vectors.length;
        int columnsNumber = vectors[0].getSize();
        fillVectors(rowsNumber, columnsNumber);

        for (int i = 0; i < rowsNumber; ++i) {
            for (int j = 0; j < columnsNumber; ++j) {
                setElement(i, j, vectors[i].getCoordinate(j));
            }
        }
    }

    private double getElement(int row, int column) {
        return vectors[row].getCoordinate(column);
    }

    private void setElement(int row, int column, double element) {
        vectors[row].setCoordinate(column, element);
    }

    private int getRowsNumber() {
        return vectors.length;
    }

    private int getColumnsNumber() {
        return vectors[0].getSize();
    }

    @Override
    public String toString() {
        return Arrays.toString(vectors);
    }

    private void fillVectors(int rowsNumber, int columnsNumber) {
        if (rowsNumber <= 0 || columnsNumber <= 0) {
            throw new IllegalArgumentException("Число строк и столбцов должно быть больше нуля.");
        }
        vectors = new Vector[rowsNumber];
        for (int i = 0; i < rowsNumber; ++i) {
            vectors[i] = new Vector(columnsNumber);
        }
    }

    public Vector getVector(int index) {
        if (index < 0 || index >= getRowsNumber()) {
            throw new IllegalArgumentException("Индекс выходит за пределы строк матрицы.");
        }
        return vectors[index];
    }

    public void setVector(int index, Vector vector) {
        if (index < 0 || index >= getRowsNumber()) {
            throw new IllegalArgumentException("Индекс выходит за пределы строк матрицы.");
        }

        int vectorSize = vector.getSize();
        if (vectorSize != getColumnsNumber()) {
            throw new IllegalArgumentException("Размер передаваемого вектора не совпадает с размером строк в матрице.");
        }

        for (int i = 0; i < vectorSize; ++i) {
            setElement(index, i, vector.getCoordinate(i));
        }
    }

    public Vector getColumn(int index) {
        if (index < 0 || index >= getColumnsNumber()) {
            throw new IllegalArgumentException("Индекс выходит за пределы столбцов матрицы.");
        }

        int rowsNumber = getRowsNumber();
        double[] vectorCoordinates = new double[rowsNumber];
        for (int i = 0; i < rowsNumber; ++i) {
            vectorCoordinates[i] = getElement(i, index);
        }

        return new Vector(vectorCoordinates);
    }

    public void  transpose() {

    }

}
