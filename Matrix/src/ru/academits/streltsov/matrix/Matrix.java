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

    private Vector getVector(int index) {
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

    private Vector getColumn(int index) {
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
        int columnsNumber = getColumnsNumber();

        Vector[] columns = new Vector[columnsNumber];
        for (int i = 0; i < columnsNumber; ++i) {
            columns[i] = new Vector(getColumn(i));
        }

        System.arraycopy(columns, 0, vectors, 0, columnsNumber);
    }

    public double calculateDeterminant() {
        int rowsNumber = getRowsNumber();
        int columnsNumber = getColumnsNumber();

        if (rowsNumber != columnsNumber) {
            throw new IllegalArgumentException("Определитель вычислется только у квадратной матрицы.");
        }

        if (rowsNumber == 1) {
            return getElement(0, 0);
        }

        if (rowsNumber == 2) {
            return getElement(0,0) * getElement(1, 1) - getElement(0, 1) * getElement(1,0);
        }

        if (rowsNumber == 3) {
            double element00 = getElement(0,0);
            double element01 = getElement(0,1);
            double element02 = getElement(0,2);
            double element10 = getElement(1,0);
            double element11 = getElement(1,1);
            double element12 = getElement(1,2);
            double element20 = getElement(2,0);
            double element21 = getElement(2,1);
            double element22 = getElement(2,2);

            Matrix matrix = new Matrix(new double[][]{{element11, element12}, {element21, element22}});
            Matrix matrix1 = new Matrix(new double[][]{{element10, element12}, {element20, element22}});
            Matrix matrix2 = new Matrix(new double[][]{{element10, element11}, {element20, element21}});

            return element00 * matrix.calculateDeterminant() - element01 * matrix1.calculateDeterminant() +
                    element02 * matrix2.calculateDeterminant();
        }

        double determinant = 0;
        Vector[][] vectors = new Vector[rowsNumber][rowsNumber - 1];
        for (int r = 0; r < rowsNumber; ++r) {
            for (int i = 0; i < rowsNumber - 1; ++i) {
                vectors[r][i] = new Vector(columnsNumber - 1);
                for (int j = 0, k = 0; j < columnsNumber - 1; ++k) {
                    if (k != r) {
                        vectors[r][i].setCoordinate(j, getElement(i + 1, k));
                        ++j;
                    }
                }
            }

            Matrix[] matrices = new Matrix[rowsNumber];
            matrices[r] = new Matrix(vectors[r]);
            determinant += Math.pow(-1, r + 2) * getElement(0, r) * matrices[r].calculateDeterminant();
        }

        return determinant;
    }

    public void multiply(double scalar) {
        for (int i = 0; i < getRowsNumber(); ++i) {
            vectors[i].multiply(scalar);
        }
    }

    public Vector multiply(Vector vector) {
        if (getColumnsNumber() != vector.getSize()) {
            throw new IllegalArgumentException("Число столбцов в первой матрице должно совпадать с числом строк во второй.");
        }

        int rowsNumber = getRowsNumber();
        Vector newVector = new Vector(rowsNumber);
        for (int i = 0; i < rowsNumber; ++i) {
            newVector.setCoordinate(i, Vector.getScalarProduct(vectors[i], vector));
        }

        return newVector;
    }

    public void add(Matrix matrix) {
        int rowsNumber = getRowsNumber();

        if (getColumnsNumber() != matrix.getColumnsNumber() || rowsNumber != matrix.getRowsNumber()) {
            throw new IllegalArgumentException("Размеры матриц должны совпадать.");
        }

        for (int i = 0; i < rowsNumber; ++i) {
            vectors[i].add(matrix.getVector(i));
        }
    }

    public void deduct(Matrix matrix) {
        int rowsNumber = getRowsNumber();

        if (getColumnsNumber() != matrix.getColumnsNumber() || rowsNumber != matrix.getRowsNumber()) {
            throw new IllegalArgumentException("Размеры матриц должны совпадать.");
        }

        for (int i = 0; i < rowsNumber; ++i) {
            vectors[i].deduct(matrix.getVector(i));
        }
    }

    public static Matrix sum(Matrix matrix1, Matrix matrix2) {
        Matrix matrix = new Matrix(matrix1);
        matrix.add(matrix2);
        return matrix;
    }

    public static Matrix difference(Matrix matrix1, Matrix matrix2) {
        Matrix matrix = new Matrix(matrix1);
        matrix.deduct(matrix2);
        return matrix;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        int rowsNumber = matrix1.getRowsNumber();
        int columnsNumber = matrix2.getColumnsNumber();

        Matrix matrix = new Matrix(rowsNumber, columnsNumber);
        for (int i = 0; i < rowsNumber; ++i) {
            for (int j = 0; j < columnsNumber; ++j) {
                matrix.setElement(i, j, matrix1.multiply(matrix2.getColumn(j)).getCoordinate(i));
            }
        }

        return matrix;
    }
}
