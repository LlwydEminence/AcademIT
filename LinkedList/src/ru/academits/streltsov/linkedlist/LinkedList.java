package ru.academits.streltsov.linkedlist;

import java.util.*;

public class LinkedList<T> implements List<T>, Deque<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    private int modCount;

    public LinkedList() {
    }

    public LinkedList(T data) {
        head = new Node<>(data);
        tail = head;
        size = 1;
    }

    private void checkForEmptyList() {
        if (head == null) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (Node<T> p = head; p != null; p = p.getNext()) {
            stringBuilder.append(p.getData());
            if (p.getNext() != null) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void addFirst(T t) {
        if (head == null) {
            head = new Node<>(t);
            tail = head;
        } else {
            Node<T> p = new Node<>(t);
            p.setNext(head);
            head.setPrevious(p);
            head = p;
        }
        ++size;
        ++modCount;
    }

    @Override
    public void addLast(T t) {
        if (tail == null) {
            head = new Node<>(t);
            tail = head;
        } else {
            Node<T> p = new Node<>(t);
            p.setPrevious(tail);
            tail.setNext(p);
            tail = p;
        }
        ++size;
        ++modCount;
    }

    @Override
    public boolean offerFirst(T t) {
        addFirst(t);
        return true;
    }

    @Override
    public boolean offerLast(T t) {
        addLast(t);
        return true;
    }

    private T removeHead() {
        T deletedData = head.getData();

        if (head == tail) {
            head = null;
        } else {
            head = head.getNext();
            head.setPrevious(null);
        }
        --size;
        ++modCount;
        return deletedData;
    }

    private T removeTail() {
        T deletedData = tail.getData();
        if (head == tail) {
            tail = null;
        } else {
            tail = tail.getPrevious();
            tail.setNext(null);
        }
        --size;
        ++modCount;
        return deletedData;
    }

    @Override
    public T removeFirst() {
        checkForEmptyList();
        return removeHead();
    }

    @Override
    public T removeLast() {
        checkForEmptyList();
        return removeTail();
    }

    @Override
    public T pollFirst() {
        if (head == null) {
            return null;
        }

        return removeHead();
    }

    @Override
    public T pollLast() {
        if (tail == null) {
            return null;
        }

        return removeTail();
    }

    @Override
    public T getFirst() {
        checkForEmptyList();
        return head.getData();
    }

    @Override
    public T getLast() {
        checkForEmptyList();
        return tail.getData();
    }

    @Override
    public T peekFirst() {
        if (head == null) {
            return null;
        }

        return head.getData();
    }

    @Override
    public T peekLast() {
        if (tail == null) {
            return null;
        }

        return tail.getData();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        checkForEmptyList();
        if (Objects.equals(head.getData(), o)) {
            removeHead();
            return true;
        } else if (head == tail) {
            return false;
        }

        for (Node<T> p = head.getNext(); p.getNext() != null; p = p.getNext()) {
            if (Objects.equals(p.getData(), o)) {
                p.getPrevious().setNext(p.getNext());
                p.getNext().setPrevious(p.getPrevious());
                --size;
                ++modCount;
                return true;
            }
        }

        if (Objects.equals(tail.getData(), o)) {
            removeTail();
            return true;
        }

        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        checkForEmptyList();
        if (Objects.equals(tail.getData(), o)) {
            removeTail();
            return true;
        } else if (head == tail) {
            return false;
        }

        for (Node<T> p = tail.getPrevious(); p.getPrevious() != null; p = p.getPrevious()) {
            if (Objects.equals(p.getData(), o)) {
                p.getPrevious().setNext(p.getNext());
                p.getNext().setPrevious(p.getPrevious());
                --size;
                ++modCount;
                return true;
            }
        }

        if (Objects.equals(head.getData(), o)) {
            removeHead();
            return true;
        }

        return false;
    }

    @Override
    public boolean offer(T t) {
        offerLast(t);
        return true;
    }

    @Override
    public T remove() {
        return removeFirst();
    }

    @Override
    public T poll() {
        return pollFirst();
    }

    @Override
    public T element() {
        return getFirst();
    }

    @Override
    public T peek() {
        return peekFirst();
    }

    @Override
    public void push(T t) {
        addFirst(t);
    }

    @Override
    public T pop() {
        return removeFirst();
    }

    @Override
    public Iterator<T> descendingIterator() {
        return new DescItr();
    }

    private class DescItr implements Iterator<T> {
        private final ListItr itr = new ListItr(size - 1);

        @Override
        public boolean hasNext() {
            return itr.hasPrevious();
        }

        @Override
        public T next() {
            return itr.previous();
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        private final ListItr itr = new ListItr(0);

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

        @Override
        public T next() {
            return itr.next();
        }

        @Override
        public void remove() {
            itr.remove();
        }
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (T t : this) {
            array[i] = t;
            ++i;
        }
        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        Objects.requireNonNull(a);

        int aLength = a.length;
        if (aLength < size) {
            //noinspection unchecked
            return (T1[]) Arrays.copyOf(toArray(), size, a.getClass());
        }
        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(toArray(), 0, a, 0, size);
        if (aLength > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean add(T t) {
        offerLast(t);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Objects.requireNonNull(c);

        for (Object aC : c) {
            if (!contains(aC)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Objects.requireNonNull(c);

        int expectedModCount = modCount;

        for (T aC : c) {
            if (aC != null) {
                add(aC);
            }
        }
        return expectedModCount != modCount;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        Objects.requireNonNull(c);
        int expectedModCount = modCount;

        ListIterator<T> listIterator = listIterator(index);
        for (T aC : c) {
            listIterator.add(aC);
        }

        return expectedModCount != modCount;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);

        int expectedModCount = modCount;
        for (Iterator<T> iterator = iterator(); iterator.hasNext(); ) {
            if (c.contains(iterator.next())) {
                iterator.remove();
            }
        }
        //removeIf(c::contains);
        return expectedModCount != modCount;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);

        int expectedModCount = modCount;
        for (Iterator<T> iterator = iterator(); iterator.hasNext(); ) {
            if (!c.contains(iterator.next())) {
                iterator.remove();
            }
        }
        //removeIf(c::contains);
        return expectedModCount != modCount;
    }

    @Override
    public void clear() {
        ++modCount;

        if (head == null) {
            return;
        }

        for (Node<T> p = head, q = p.getNext(); q != null; q = q.getNext()) {
            p.setNext(null);
            q.setPrevious(null);
            p = q;
        }

        head = null;
        tail = null;
    }

    @Override
    public T get(int index) {
        return getNode(index).getData();
    }

    @Override
    public T set(int index, T element) {
        Node <T> p = getNode(index);

        T oldData = p.getData();
        p.setData(element);

        return oldData;
    }

    @Override
    public void add(int index, T element) {
        if (index == size) {
            addLast(element);
            return;
        }

        if (index == 0) {
            addFirst(element);
            return;
        }

        Node<T> q = getNode(index);
        Node<T> p = new Node<>(element);
        p.setPrevious(q.getPrevious());
        q.getPrevious().setNext(p);
        q.setPrevious(p);
        p.setNext(q);
        ++size;
        ++modCount;
    }

    @Override
    public T remove(int index) {
        if (index == 0) {
            return removeFirst();
        }

        if (index == size - 1) {
            return removeLast();
        }

        Node<T> p = getNode(index);
        T deletedData = p.getData();
        p.getPrevious().setNext(p.getNext());
        p.getNext().setPrevious(p.getPrevious());
        ++modCount;
        --size;
        return deletedData;
    }

    @Override
    public int indexOf(Object o) {
        checkForEmptyList();
        int i = -1;
        for (Node<T> p = head; p != null; p = p.getNext()) {
            ++i;
            if (Objects.equals(p.getData(), o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        checkForEmptyList();
        int i = size;
        for (Node<T> p = tail; p != null; p = p.getPrevious()) {
            --i;
            if (o == null ? p.getData() == null : p.getData().equals(o)) {
                return i;
            }
        }

        return -1;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index < size / 2) {
            Node<T> p = head;
            for (int i = 0; i < index; ++i) {
                p = p.getNext();
            }
            return p;
        } else {
            Node<T> p = tail;
            for (int i = size - 1; i > index; --i) {
                p = p.getPrevious();
            }
            return p;
        }
    }

    private T unlink(Node<T> x) {
        final T element = x.getData();
        final Node<T> next = x.getNext();
        final Node<T> prev = x.getPrevious();

        if (prev == null) {
            head = next;
        } else {
            prev.setNext(next);
            x.setPrevious(null);
        }

        if (next == null) {
            tail = prev;
        } else {
            next.setPrevious(prev);
            x.setNext(null);
        }

        x.setData(null);
        --size;
        ++modCount;
        return element;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListItr(0);
    }

    private class ListItr implements ListIterator<T> {
        private Node<T> next;
        private Node<T> previous;
        private int expectedModCount = modCount;
        private int nextIndex;

        ListItr(int index) {
            if (index == size) {
                next = null;
            } else {
                next = getNode(index);
            }
            nextIndex = index;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public T next() {
            checkForComodification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            previous = next;
            next = next.getNext();
            ++nextIndex;
            return previous.getData();
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public T previous() {
            checkForComodification();
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }

            if (next == null) {
                previous = tail;
                next = tail;
            } else {
                previous = next.getPrevious();
                next = next.getPrevious();
                --nextIndex;
                return previous.getData();
            }
            return null;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            checkForComodification();
            if (previous == null) {
                throw new IllegalStateException();
            }

            Node<T> lastNext = previous.getNext();
            unlink(previous);
            if (next == previous) {
                next = lastNext;
            } else {
                --nextIndex;
            }
            previous = null;
            ++expectedModCount;
        }

        @Override
        public void set(T t) {
            if (previous == null) {
                throw new IllegalStateException();
            }
            checkForComodification();
            previous.setData(t);
        }

        @Override
        public void add(T t) {
            checkForComodification();
            previous = null;
            if (next == null) {
                addLast(t);
            } else {
                Node<T> pred = next.getPrevious();
                Node<T> newNode = new Node<>(t);
                newNode.setPrevious(pred);
                newNode.setNext(next);
                next.setPrevious(newNode);
                if (pred == null) {
                    head = newNode;
                } else {
                    pred.setNext(newNode);
                }

                ++size;
                ++modCount;
            }
            ++nextIndex;
            ++expectedModCount;
        }

        final void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Метод subList не определен.");
    }
}
