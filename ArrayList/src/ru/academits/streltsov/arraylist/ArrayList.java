package ru.academits.streltsov.arraylist;

import java.util.*;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private T[] items;
    private int length;
    private int modCount;

    public ArrayList() {
        //noinspection unchecked
        items = (T[]) new Object[DEFAULT_CAPACITY];
    }

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Вместимость не может быть меньше нуля.");
        } else {
            //noinspection unchecked
            items = (T[]) new Object[capacity];
        }
    }

    private void checkIndexInBounds(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
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
        int currentPosition;
        int previousPosition = -1;
        int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            return currentPosition != length;
        }

        @Override
        public T next() {
            checkForListModification();
            int i = currentPosition;
            if (i >= length) {
                throw new NoSuchElementException("Достигнут конец списка.");
            }
            T[] items = ArrayList.this.items;
            ++currentPosition;
            previousPosition = i;
            return items[previousPosition];
        }

        @Override
        public void remove() {
            if (previousPosition < 0) {
                throw new IllegalStateException("Сначала нужно вызвать метод next");
            }
            checkForListModification();
            ArrayList.this.remove(previousPosition);
            currentPosition = previousPosition;
            previousPosition = -1;
            expectedModCount = modCount;
        }

        void checkForListModification() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException("Список был изменён.");
            }
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, length);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        Objects.requireNonNull(a);

        int aLength = a.length;
        if (aLength >= length) {
            //noinspection SuspiciousSystemArraycopy
            System.arraycopy(items, 0, a, 0, length);
        } else {
            //noinspection unchecked
            a = (T1[]) new Object[length];
            //noinspection SuspiciousSystemArraycopy
            System.arraycopy(items, 0, a, 0, length);
        }
        //noinspection unchecked
        return a;
    }

    @Override
    public boolean add(T t) {
        if (length >= items.length) {
            increaseCapacity();
        }
        //noinspection unchecked
        items[length] = t;
        ++length;
        ++modCount;
        return true;
    }

    private void increaseCapacity() {
        T[] old = items;
        //noinspection unchecked
        items = (T[]) new Object[old.length * 2];
        System.arraycopy(old, 0, items, 0, old.length);
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < items.length; ++i) {
            if (o == null ? items[i] == null : items[i].equals(o)) {
                remove(i);
                ++modCount;
                return true;
            }
        }
        return false;
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

        Object[] collectionArray = c.toArray();
        int collectionLength = collectionArray.length;

        ensureCapacity(length + collectionLength);
        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(collectionArray, 0, items, length, collectionLength);
        length += collectionLength;
        ++modCount;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException();
        }
        Objects.requireNonNull(c);

        if (index == length) {
            return addAll(c);
        }

        Object[] a = c.toArray();
        int collectionLength = a.length;
        ensureCapacity(length + collectionLength);
        int addedPartLastIndex = index + collectionLength - 1;
        System.arraycopy(items, index, items, addedPartLastIndex + 1, length - index);
        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(a, 0, items, index, collectionLength);
        length += collectionLength;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        int expectedModCount = modCount;
        for (int i = 0; i < length; ++i) {
            if (c.contains(items[i])) {
                remove(i);
                --i;
            }
        }

        return expectedModCount != modCount;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        int expectedModCount = modCount;
        for (int i = 0; i < length; ++i) {
            if (!c.contains(items[i])) {
                remove(i);
                --i;
            }
        }

        return expectedModCount != modCount;
    }

    @Override
    public void clear() {
        for (int i = 0; i < length; ++i) {
            items[i] = null;
        }
        length = 0;
        ++modCount;
    }

    @Override
    public T get(int index) {
        checkIndexInBounds(index);
        return items[index];
    }

    @Override
    public T set(int index, T element) {
        checkIndexInBounds(index);
        T old = items[index];
        //noinspection unchecked
        items[index] = element;
        return old;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException();
        }
        if (index == length) {
            add(element);
            return;
        }
        if (length >= items.length) {
            increaseCapacity();
        }
        System.arraycopy(items, index, items, index + 1, length - index);
        //noinspection unchecked
        items[index] = element;
        ++modCount;
    }

    @Override
    public T remove(int index) {
        checkIndexInBounds(index);
        if (index < length - 1) {
            System.arraycopy(items, index + 1, items, index, length - index - 1);
        }
        --length;
        ++modCount;
        return items[index];
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < length; ++i) {
            if (o == null ? items[i] == null : items[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = length - 1; i >= 0; --i) {
            if (o == null ? items[i] == null : items[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListItr(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListItr(index);
    }

    private class ListItr extends Itr implements ListIterator<T> {
        ListItr(int index) {
            super();
            currentPosition = index;
        }


        @Override
        public boolean hasPrevious() {
            return currentPosition != 0;
        }

        @Override
        public T previous() {
            checkForListModification();
            int i = currentPosition - 1;
            if (i < 0) {
                throw new NoSuchElementException("Выход за пределы списка.");
            }
            Object[] items = ArrayList.this.items;
            currentPosition = i;
            //noinspection unchecked
            return (T) items[previousPosition = i];
        }

        @Override
        public int nextIndex() {
            return currentPosition;
        }

        @Override
        public int previousIndex() {
            return currentPosition - 1;
        }

        @Override
        public void set(T t) {
            if (previousPosition < 0) {
                throw new IllegalStateException("Сначала необходимо вызвать метод next");
            }

            checkForListModification();
            ArrayList.this.set(previousPosition, t);
        }

        @Override
        public void add(T t) {
            checkForListModification();
            int i = currentPosition;
            ArrayList.this.add(i, t);
            ++currentPosition;
            previousPosition = -1;
            expectedModCount = modCount;
        }
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Метод subList не определен.");
    }

    private void ensureCapacity(int capacity) {
        if (items.length < capacity) {
            items = Arrays.copyOf(items, capacity);
        }
    }

    public void trimToSize() {
        if (items.length > length) {
            items = Arrays.copyOf(items, length);
            ++modCount;
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(items);
    }
}
