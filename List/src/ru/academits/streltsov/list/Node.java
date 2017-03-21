package ru.academits.streltsov.list;

class Node {
    private int data;
    private Node next;

    Node(int data) {
        this.data = data;
        next = null;
    }

    int getData() {
        return data;
    }

    Node getNext() {
        return next;
    }

    void setData(int data) {
        this.data = data;
    }

    void setNext(Node next) {
        this.next = next;
    }
}
