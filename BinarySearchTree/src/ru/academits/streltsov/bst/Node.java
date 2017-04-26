package ru.academits.streltsov.bst;

class Node<T extends Comparable> {
    private T data;
    private Node<T> left;
    private Node<T> right;

    Node(T data) {
        this.data = data;
    }

    T getData() {
        return data;
    }

    Node<T> getLeft() {
        return left;
    }

    Node<T> getRight() {
        return right;
    }

    void setLeft(Node<T> node) {
        left = node;
    }

    void setRight(Node<T> node) {
        right = node;
    }

    void setData(T data) {
        this.data = data;
    }
}
