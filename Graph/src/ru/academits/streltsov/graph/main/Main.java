package ru.academits.streltsov.graph.main;

import ru.academits.streltsov.graph.Graph;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(new int[][]{{0, 0, 1, 1, 0}, {0, 0, 1, 0, 0}, {1, 1, 0, 0, 1}, {1, 0, 0, 0, 1}, {0, 0, 1, 1, 0}});
        Graph graph1 = new Graph(new int[][]{{0, 1, 0, 0, 0, 0, 0}, {1, 0, 1, 1, 1, 1, 0}, {0, 1, 0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 1, 0}, {0, 1, 0, 0, 1, 0, 1}, {0, 0, 1, 0, 0, 1, 0}});

        //graph1.recursiveDepthFirstSearch();
        System.out.println();
        graph1.depthFirstSearch();
    }
}
