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
        ArrayList<Integer> visitedVertices = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < vertexNumber; ++i) {
            if (!visitedVertices.contains(i)) {
                queue.add(i);
                visitedVertices.add(i);
                printIndex(queue.remove() + 1);
            }

            for (int j = i + 1; j < vertexNumber; ++j) {
                if (adjacencyMatrix[i][j] == 1 && !visitedVertices.contains(j)) {
                    queue.add(j);
                    visitedVertices.add(j);
                }
            }

            while (!queue.isEmpty()) {
                printIndex(queue.remove() + 1);
            }

            if (visitedVertices.size() == vertexNumber) {
                return;
            }
        }
    }

    public void recursiveDepthFirstSearch() {
        int[] mark = new int[vertexNumber];
        recursiveDepthFirstSearch(0, mark);
    }

    private void recursiveDepthFirstSearch(int index, int[] mark) {
        if (mark[index] != 0) {
            return;
        }

        printIndex(index + 1);
        mark[index] = 1;

        for (int i = 0; i < vertexNumber; ++i) {
            if (adjacencyMatrix[index][i] == 1) {
                recursiveDepthFirstSearch(i, mark);
            }
        }
    }

    public void depthFirstSearch() {
        int[] mark = new int[vertexNumber];
        Deque<Integer> stack = new LinkedList<>();
        int index = 0;

        mark[index] = 1;
        printIndex(index + 1);

        do {
            for (int i = 0; i < vertexNumber; ++i) {
                if (adjacencyMatrix[index][i] == 1 && mark[i] == 0) {
                    stack.addLast(index);
                    index = i;
                    i = -1;
                    mark[index] = 1;
                    printIndex(index + 1);
                }
            }

            index = stack.removeLast();
        } while (!stack.isEmpty());


    }

}
