package ru.academits.streltsov.list;

public class SinglyLinkedList<T> {
    private Node<T> head;
    private int size;

    public SinglyLinkedList(T data) {
        head = new Node<>(data);
        size = 1;
    }

    private SinglyLinkedList() {
        head = null;
        size = 0;
    }

    private Node<T> getHead() {
        return head;
    }

    private int getSize() {
        return size;
    }

    private T getData(int index) {
        return getNode(index).getData();
    }

    public T setData(int index, T data) {
        Node<T> node = getNode(index);
        T oldData = node.getData();
        node.setData(data);
        return oldData;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Индекс выходит за пределы списка.");
        }

        int i = 0;
        Node<T> p;
        for (p = head; p != null; p = p.getNext(), ++i) {
            if (i == index) {
                break;
            }
        }
        return p;
    }

    public T deleteData(int index) throws Exception {
        if (size == 0) {
            throw new Exception("Список пуст.");
        }

        if (index >= size) {
            throw new IllegalArgumentException("Индекс выходит за пределы списка");
        }

        if (index == 0) {
            return deleteHead();
        }

        Node<T> node = getNode(index - 1);
        T deletedData = node.getNext().getData();
        node.setNext(node.getNext().getNext());
        --size;
        return deletedData;
    }

    public void insertToTop(T data) {
        if (size == 0) {
            head = new Node<>(data);
            ++size;
            return;
        }
        Node<T> p = new Node<>(data);
        p.setNext(head.getNext());
        head = p;
        ++size;
    }

    public void insert(int index, T data) {
        Node<T> node = getNode(index - 1);
        Node<T> p = new Node<>(data);
        p.setNext(node.getNext());
        node.setNext(p);
        ++size;
    }

    public void deleteNode(T data) throws Exception {
        if (size == 0) {
            throw new Exception("Список пуст.");
        }

        if (head.getData() == data) {
            deleteHead();
            return;
        }

        for (Node<T> p = head; p.getNext() != null; p = p.getNext()) {
            if (p.getNext().getData() == data) {
                p.setNext(p.getNext().getNext());
                --size;
                return;
            }
        }

        throw new IllegalArgumentException("Индекс выходит за пределы списка.");
    }

    private T deleteHead() throws Exception {
        if (size == 0) {
            throw new Exception("Список пуст.");
        }

        T deletedData = head.getData();
        head = head.getNext();
        --size;
        return deletedData;
    }

    public void insertAfter(Node<T> node, T data) {
        if (size == 0) {
            throw new IllegalArgumentException("Спиок пуст");
        }

        T nodeData = node.getData();
        for (Node<T> p = head; p != null; p = p.getNext()) {
            if (p.getData() == nodeData) {
                Node<T> q = new Node<>(data);
                q.setNext(node.getNext());
                node.setNext(q);
                ++size;
                return;
            }
        }

        throw new IllegalArgumentException("Узел отсутствует в списке");
    }

    public void deleteAfter(Node<T> node) throws Exception {
        if (size == 0) {
            throw new Exception("Список пуст.");
        }

        T nodeData = node.getData();
        for (Node<T> p = head; p.getNext() != null; p = p.getNext()) {
            if (p.getData() == nodeData) {
                p.setNext(p.getNext().getNext());
                --size;
                return;
            }
        }
        throw new IllegalArgumentException("Узел отсутсвует или является последнем узлом в списке.");
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for (Node p = head; p != null; p = p.getNext()) {
            stringBuilder.append(p.getData());
            if (p.getNext() != null) {
                stringBuilder.append(", ");
            } else {
                stringBuilder.append("}");
            }
        }
        return stringBuilder.toString();
    }

    public void reverse() {
        if (head.getNext() == null) {
            return;
        }

        Node<T> p, q;
        for (p = head, q = head.getNext(); q != null;) {
            Node<T> r = q.getNext();
            q.setNext(p);
            p = q;
            q = r;
        }
        head.setNext(null);
        head = p;
    }

    public SinglyLinkedList<T> copy() throws Exception {
        if (size == 0) {
            return new SinglyLinkedList<>();
        }

        SinglyLinkedList<T> singlyLinkedList = new SinglyLinkedList<>(getHead().getData());
        for (Node<T> p = head.getNext(), q = singlyLinkedList.head; p != null; p = p.getNext(), q = q.getNext()) {
            q.setNext(new Node<>(p.getData()));
        }
        return singlyLinkedList;
    }
}