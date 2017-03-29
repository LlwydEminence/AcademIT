package ru.academits.streltsov.matrix.main;

import ru.academits.streltsov.matrix.Matrix;

public class Main {
    public static void main(String[] args) {
        double[][] array = new double[][] {{2, 4, 0}, {-2, 1, 3}};
        Matrix matrix = new Matrix(array);
        System.out.println(matrix);
        matrix.transpose();
        System.out.println(matrix);
    }
}
