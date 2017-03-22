package ru.academits.streltsov.list;

public class SinglyLinkedList<T> {
    private Node<T> head;
    private int size;

    public SinglyLinkedList(T data) {
        head = new Node<>(data);
        size = 1;
    }

    private Node<T> getHead() {
        return head;
    }

    private int getSize() {
        int size = 0;

        for (Node p = head; p != null; p = p.getNext()) {
            ++size;
        }

        return size;
    }

    private T getData(int index) {
        return getNode(index).getData();
    }

    public T setData(int index, T data) {
        T oldData = getData(index);
        getNode(index).setData(data);
        return oldData;
    }

    private Node<T> getNode(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Индекс меньше нуля.");
        }

        int i = 0;
        for (Node<T> p = head; p != null; p = p.getNext(), ++i) {
            if (i == index) {
                return p;
            }
        }

        throw new IllegalArgumentException("Индекс выходит за пределы списка.");
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

    public void insertAfter(int index, T data) {
        Node<T> node = getNode(index);
        Node<T> p = new Node<>(data);
        p.setNext(node.getNext());
        node.setNext(p);
        ++size;
    }

    public void deleteAfter(int index) throws Exception {
        if (size == 0) {
            throw new Exception("Список пуст.");
        }

        if (index >= size) {
            throw new IllegalArgumentException("Индекс выходит за пределы списка.");
        }

        if (index == -1) {
            deleteHead();
            return;
        }

        Node<T> node = getNode(index);
        node.setNext(node.getNext().getNext());
        --size;

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

        for (Node<T> p = head, q = p.getNext();;) {
            if (q.getNext() != null) {
                Node<T> r = q.getNext();
                q.setNext(p);
                p = r.getNext();
                r.setNext(q);

                if (p == null) {
                    head.setNext(null);
                    head = r;
                    return;
                } else if (p.getNext() == null) {
                    p.setNext(r);
                    head.setNext(null);
                    head = p;
                    return;
                } else {
                    q = p.getNext();
                    p.setNext(r);
                }
            } else {
                q.setNext(p);
                head.setNext(null);
                head = q;
                return;
            }
        }
    }

    public SinglyLinkedList<T> copy() throws Exception {
        if (size == 0) {
            throw new Exception("Список пуст.");
        }

        SinglyLinkedList<T> singlyLinkedList = new SinglyLinkedList<>(getHead().getData());
        for (int i = 1; i < size; ++i) {
            singlyLinkedList.insert(i, getData(i));
        }
        return singlyLinkedList;
    }
}