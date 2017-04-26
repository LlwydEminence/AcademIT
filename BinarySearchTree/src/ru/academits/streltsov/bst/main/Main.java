package ru.academits.streltsov.bst.main;

import ru.academits.streltsov.bst.BinarySearchTree;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(5);
        bst.add(3);
        bst.add(9);
        bst.add(16);
        bst.add(1);
        bst.add(2);
        bst.add(7);
        bst.add(4);
        bst.remove(3);
        bst.remove(9);
        bst.breadthFirstSearch();
    }
}
