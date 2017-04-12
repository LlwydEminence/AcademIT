package ru.academits.streltsov.linkedlist;

class Node<T> {
    private T data;
    private Node<T> next;
    private Node<T> previous;

    Node(T data) {
        this.data = data;
    }

    T getData() {
        return data;
    }

    void setData(T data) {
        this.data = data;
    }

    Node<T> getNext() {
        return next;
    }

    void setNext(Node<T> next) {
        this.next = next;
    }

    Node<T> getPrevious() {
        return previous;
    }

    void setPrevious(Node<T> previous) {
        this.previous = previous;
    }
}
