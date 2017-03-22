package ru.academits.streltsov.list;

class Node<T> {
    private T data;
    private Node<T> next;

    Node(T data) {
        this.data = data;
        next = null;
    }

    T getData() {
        return data;
    }

    Node<T> getNext() {
        return next;
    }

    void setData(T data) {
        this.data = data;
    }

    void setNext(Node<T> next) {
        this.next = next;
    }
}
