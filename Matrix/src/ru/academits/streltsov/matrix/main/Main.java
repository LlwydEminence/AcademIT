package ru.academits.streltsov.matrix.main;

import ru.academits.streltsov.matrix.Matrix;

public class Main {
    public static void main(String[] args) {
        double[][] array = new double[][] {{1, 2}, {4, 5}, {3, 6}};
        double[][] array1 = new double[][] {{1, 2, 3}, {4, 5, 6}};
        Matrix matrix = new Matrix(array);
        Matrix matrix1 = new Matrix(array1);
        System.out.println(Matrix.getProduct(matrix, matrix1));
        matrix.transpose();
        System.out.println(matrix);
    }
}
