package ru.academits.streltsov.list;

public class SinglyLinkedList {
    private Node head;

    public SinglyLinkedList(int data) {
        head = new Node(data);
    }

    public Node getHead() {
        return head;
    }

    private int getSize() {
        int size = 0;

        for (Node p = head; p != null; p = p.getNext()) {
            ++size;
        }

        return size;
    }

    public int getData(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Индекс меньше нуля.");
        }

        int i = 0;
        for (Node p = head; p != null; p = p.getNext(), ++i) {
            if (i == index) {
                return p.getData();
            }
        }

        throw new IllegalArgumentException("Индекс выходит за пределы списка.");
    }

    public int getData(int index, int data) {
        if (index < 0) {
            throw new IllegalArgumentException("Индекс меньше нуля.");
        }

        int i = 0;
        for (Node p = head; p != null; p = p.getNext(), ++i) {
            if (i == index) {
                int oldData = p.getData();
                p.setData(data);
                return oldData;
            }
        }

        throw new IllegalArgumentException("Индекс выходит за пределы списка.");
    }

    public Node getNode(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Индекс меньше нуля.");
        }

        int i = 0;
        for (Node p = head; p != null; p = p.getNext(), ++i) {
            if (i == index) {
                return p;
            }
        }

        throw new IllegalArgumentException("Индекс выходит за пределы списка.");
    }

    public int deleteData(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Индекс меньше нуля.");
        }

        if (index == 0) {
            int deletedData = head.getData();
            head = head.getNext();
            return deletedData;
        }

        int i = 0;
        for (Node p = head; p != null && i < index - 1; p = p.getNext(), ++i) {
            if (index == i + 1) {
                int deletedData = p.getNext().getData();
                p.setNext(p.getNext().getNext());
                return deletedData;
            }
        }

        throw new IllegalArgumentException("Индекс выходит за пределы списка.");
    }

    public void insertToTop(int data) {
        Node p = new Node(data);
        p.setNext(head.getNext());
        head = p;
    }

    public void insert(int index, int data) {
        if (index < 0) {
            throw new IllegalArgumentException("Индекс меньше нуля.");
        }

        if (index > getSize()) {
            throw new IllegalArgumentException("Индекс превышает последний элемент списка более чем на 1.");
        }

        Node p = new Node(data);
        int i = 0;
        for (Node q = head; q != null; q = q.getNext(), ++i) {
            if (index == i + 1) {
                p.setNext(q.getNext());
                q.setNext(p);
                return;
            }
        }
    }

    public void deleteNode(int data) {
        if (head.getData() == data) {
            deleteHead();
            return;
        }

        for (Node p = head; p.getNext() != null; p = p.getNext()) {
            if (p.getNext().getData() == data) {
                p.setNext(p.getNext().getNext());
                return;
            }
        }

        throw new IllegalArgumentException("Индекс выходит за пределы списка.");
    }

    public int deleteHead() {
        int deletedData = head.getData();
        head = head.getNext();
        return deletedData;
    }

    public void insertAfter(int index, int data) {
        if (index < 0) {
            throw new IllegalArgumentException("Индекс меньше нуля.");
        }

        if (index >= getSize()) {
            throw new IllegalArgumentException("Индекс выходит за пределы списка.");
        }

        Node p = new Node(data);
        int i = 0;
        for (Node q = head; q != null; q = q.getNext(), ++i) {
            if (index == i) {
                p.setNext(q.getNext());
                q.setNext(p);
                return;
            }
        }
    }

    public void deleteAfter(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Индекс меньше нуля.");
        }

        if (index == 0) {
            deleteHead();
            return;
        }

        int i = 1;
        for (Node p = head.getNext(); p.getNext() != null; p = p.getNext(), ++i) {
            if (index == i) {
                p.setNext(p.getNext().getNext());
                return;
            }
        }

        throw new IllegalArgumentException("Индекс выходит за пределы списка.");
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

        for (Node p = head, q = p.getNext();;) {
            if (q.getNext() != null) {
                Node r = q.getNext();
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

    public void copyOf(SinglyLinkedList singlyLinkedList) {
        int i = 0;
        for (Node p = singlyLinkedList.getHead(), q = head; p != null; p = p.getNext(), ++i) {
            if (q != null) {
                q.setData(p.getData());
                q = q.getNext();
            } else {
                insert(i, p.getData());
            }
        }
    }
}