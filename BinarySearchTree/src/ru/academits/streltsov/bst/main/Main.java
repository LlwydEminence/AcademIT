package ru.academits.streltsov.bst.main;

import ru.academits.streltsov.bst.BinarySearchTree;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(5);
        bst.add(9);
        bst.add(null);
        bst.add(31);
        bst.add(5);
        bst.add(null);
        bst.add(2);
        bst.add(1);
        bst.add(3);
        bst.recursivePrefixTraverse();
        System.out.println();
        bst.prefixTraverse();
    }
}
