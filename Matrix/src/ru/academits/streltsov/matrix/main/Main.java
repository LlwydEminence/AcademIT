package ru.academits.streltsov.matrix.main;

import ru.academits.streltsov.matrix.Matrix;

public class Main {
    public static void main(String[] args) {
        double[][] array = new double[][] {{2, 4}, {-2, 1}};
        double[][] array1 = new double[][] {{2, 4}, {-2, 1}};
        Matrix matrix = new Matrix(array);
        Matrix matrix1 = new Matrix(array1);
        System.out.println(Matrix.getProduct(matrix, matrix1));
        matrix.transpose();
        System.out.println(matrix);
    }
}
