package ru.academits.streltsov.graph;

import java.util.*;

public class Graph {
    private int[][] adjacencyMatrix;
    private int vertexNumber;


    public Graph(int[][] adjacencyMatrix) {
        if (adjacencyMatrix.length != adjacencyMatrix[0].length) {
            throw new IllegalArgumentException("Матрица должна быть квадратной.");
        }

        vertexNumber = adjacencyMatrix.length;
        this.adjacencyMatrix = new int[vertexNumber][vertexNumber];
        fillAdjacencyMatrix(adjacencyMatrix);
    }

    private void fillAdjacencyMatrix(int[][] adjacencyMatrix) {
        for (int i = 0; i < vertexNumber; ++i) {
            if (adjacencyMatrix[i][i] != 0) {
                throw new IllegalArgumentException("Главная диагональ матрицы должна быть заполнена нулями.");
            }

            this.adjacencyMatrix[i][i] = 0;
            for (int j = i + 1; j < vertexNumber; ++j) {
                int element = adjacencyMatrix[i][j];
                if (element != 0 && element != 1 || element != adjacencyMatrix[j][i]) {
                    throw new IllegalArgumentException("Матрица должна быть симметричной и заполненной 0 либо 1.");
                }

                this.adjacencyMatrix[i][j] = element;
                this.adjacencyMatrix[j][i] = element;
            }
        }
    }

    @Override
    public String toString() {
        return Arrays.deepToString(adjacencyMatrix);
    }

    private void printIndex(int index) {
        System.out.println(index);
    }

    public void breadthFirstSearch() {
        boolean[] mark = new boolean[vertexNumber];
        breadthFirstSearch(0, mark);

        for (int i = 0; i < vertexNumber; ++i) {
            if (!mark[i]) {
                breadthFirstSearch(i, mark);
            }
        }
    }

    private void breadthFirstSearch(int index, boolean[] mark) {
        Queue<Integer> queue = new LinkedList<>();

        mark[index] = true;
        queue.add(index);

        while (!queue.isEmpty()) {
            index = queue.remove();
            printIndex(index);

            for (int i = 0; i < vertexNumber; ++i) {
                if (adjacencyMatrix[index][i] == 1 && !mark[i]) {
                    mark[i] = true;
                    queue.add(i);
                }
            }
        }
    }

    public void recursiveDepthFirstSearch() {
        boolean[] mark = new boolean[vertexNumber];
        recursiveDepthFirstSearch(0, mark);

        for (int i = 0; i < mark.length; ++i) {
            if (!mark[i]) {
                recursiveDepthFirstSearch(i, mark);
            }
        }
    }

    private void recursiveDepthFirstSearch(int index, boolean[] mark) {
        if (mark[index]) {
            return;
        }

        printIndex(index);
        mark[index] = true;

        for (int i = 0; i < vertexNumber; ++i) {
            if (adjacencyMatrix[index][i] == 1) {
                recursiveDepthFirstSearch(i, mark);
            }
        }
    }

    public void depthFirstSearch() {
        boolean[] mark = new boolean[vertexNumber];
        depthFirstSearch(0, mark);

        for (int i = 0; i < vertexNumber; ++i) {
            if (!mark[i]) {
                depthFirstSearch(i, mark);
            }
        }
    }

    private void depthFirstSearch(int index, boolean[] mark) {
        Deque<Integer> stack = new LinkedList<>();

        mark[index] = true;
        printIndex(index);

        do {
            for (int i = 0; i < vertexNumber; ++i) {
                if (adjacencyMatrix[index][i] == 1 && !mark[i]) {
                    stack.addLast(index);
                    index = i;
                    i = -1;
                    mark[index] = true;
                    printIndex(index);
                }
            }

            index = stack.removeLast();
        } while (!stack.isEmpty());
    }

}
