package ru.academits.streltsov.matrix.main;

import ru.academits.streltsov.matrix.Matrix;
import ru.academits.streltsov.matrix.Vector;

public class Main {
    public static void main(String[] args) {
        double[][] array = new double[][] {{2, 4, 0}, {-2, 1, 3}, {-1, 0, 1}};
        Vector vector = new Vector(new double[]{1, 2, -1});
        Matrix matrix = new Matrix(array);
        System.out.println(matrix.multiply(vector));
        //matrix.multiply(2);
        //matrix.transpose();
        System.out.println(matrix);
        System.out.println("Определитель равен: " + matrix.calculateDeterminant());
        matrix.add(matrix);
        System.out.println(matrix);
        Matrix matrix1 = new Matrix(new double[][]{{-1, 1}, {2, 0}, {0, 3}});
        Matrix matrix2 = new Matrix(new double[][]{{3, 1, 2}, {0, -1, 4}});
        System.out.println(Matrix.getProduct(matrix1, matrix2));
    }
}
