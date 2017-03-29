package ru.academits.streltsov.matrix;

import java.util.Arrays;
import ru.academits.streltsov.vector.Vector;

public class Matrix {
    private Vector[] rows;

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

        for (int i = 1; i < rowsNumber; ++i) {
            if (elements[i].length != columnsNumber) {
                throw new IllegalArgumentException("Передан массив координат разногого размера.");
            }
        }
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

        for (int i = 1; i < rowsNumber; ++i) {
            if (vectors[i].getSize() != columnsNumber) {
                throw new IllegalArgumentException("Передан массив векторов разного размера");
            }
        }
        fillVectors(rowsNumber, columnsNumber);

        for (int i = 0; i < rowsNumber; ++i) {
            for (int j = 0; j < columnsNumber; ++j) {
                setElement(i, j, vectors[i].getCoordinate(j));
            }
        }
    }

    private double getElement(int row, int column) {
        return rows[row].getCoordinate(column);
    }

    private void setElement(int row, int column, double element) {
        rows[row].setCoordinate(column, element);
    }

    private int getRowsNumber() {
        return rows.length;
    }

    private int getColumnsNumber() {
        return rows[0].getSize();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for (int i = 0; i < getRowsNumber(); ++i) {
            stringBuilder.append(rows[i]);
            if (i == getRowsNumber() - 1) {
                stringBuilder.append("}");
            } else {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    private void fillVectors(int rowsNumber, int columnsNumber) {
        if (rowsNumber <= 0 || columnsNumber <= 0) {
            throw new IllegalArgumentException("Число строк и столбцов должно быть больше нуля.");
        }
        rows = new Vector[rowsNumber];
        for (int i = 0; i < rowsNumber; ++i) {
            rows[i] = new Vector(columnsNumber);
        }
    }

    private Vector getRow(int index) {
        if (index < 0 || index >= getRowsNumber()) {
            throw new IllegalArgumentException("Индекс выходит за пределы строк матрицы.");
        }
        return rows[index];
    }

    public void setRow(int index, Vector vector) {
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

    public void transpose() {
        int columnsNumber = getColumnsNumber();
        int rowsNumber = getRowsNumber();

        Vector[] columns = new Vector[columnsNumber];
        for (int i = 0; i < columnsNumber; ++i) {
            columns[i] = new Vector(getColumn(i));
        }

        rows = columns;
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
            rows[i].multiply(scalar);
        }
    }

    public Vector multiply(Vector vector) {
        if (getColumnsNumber() != vector.getSize()) {
            throw new IllegalArgumentException("Число столбцов в первой матрице должно совпадать с числом строк во второй.");
        }

        int rowsNumber = getRowsNumber();
        Vector newVector = new Vector(rowsNumber);
        for (int i = 0; i < rowsNumber; ++i) {
            newVector.setCoordinate(i, Vector.getScalarProduct(rows[i], vector));
        }

        return newVector;
    }

    public void add(Matrix matrix) {
        int rowsNumber = getRowsNumber();

        if (getColumnsNumber() != matrix.getColumnsNumber() || rowsNumber != matrix.getRowsNumber()) {
            throw new IllegalArgumentException("Размеры матриц должны совпадать.");
        }

        for (int i = 0; i < rowsNumber; ++i) {
            rows[i].add(matrix.getRow(i));
        }
    }

    public void deduct(Matrix matrix) {
        int rowsNumber = getRowsNumber();

        if (getColumnsNumber() != matrix.getColumnsNumber() || rowsNumber != matrix.getRowsNumber()) {
            throw new IllegalArgumentException("Размеры матриц должны совпадать.");
        }

        for (int i = 0; i < rowsNumber; ++i) {
            rows[i].deduct(matrix.getRow(i));
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
