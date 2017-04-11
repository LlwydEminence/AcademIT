package ru.academits.streltsov.arraylist;

import java.util.*;

public class ArrayList<T> implements List {
    private static final int DEFAULT_CAPACITY = 10;
    private T[] items;
    private int length;
    private int modCount;

    public ArrayList() {
        length = 0;
        //noinspection unchecked
        items = (T[]) new Object[DEFAULT_CAPACITY];
        modCount = 0;
    }

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Вместимость не может быть меньше нуля.");
        } else if (capacity == 0) {
            length = 0;
            modCount = 0;
        } else {
            //noinspection unchecked
            items = (T[]) new Object[capacity];
            length = 0;
            modCount = 0;
        }
    }

    private void checkIndexInBounds(int index) {
        if (index < 0 || index > length) {
            throw new IllegalArgumentException("Индекс выходит за пределы списка");
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
        for (int i = 0; i < length; ++i) {
            if (o == null ? items[i] == null : items[i].equals(o)) {
                return true;
            }
        }
        return false;
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
            return items[previousPosition = i];
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
    public boolean add(Object o) {
        if (length >= items.length) {
            increaseCapacity();
        }
        //noinspection unchecked
        items[length] = (T) o;
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
        int expectedModCount = modCount;
        for (int i = 0; i < items.length; ++i) {
            if (o == null ? items[i] == null : items[i].equals(o)) {
                remove(i);
            }
        }
        return expectedModCount != modCount;
    }

    @Override
    public boolean addAll(Collection c) {
        if (c == null) {
            return false;
        }
        Object[] a = c.toArray();
        int oldLength = length;
        for (Object e : a) {
            add(e);
        }

        return oldLength != length;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        checkIndexInBounds(index);
        if (c == null) {
            return false;
        }
        Object[] a = c.toArray();
        int collectionLength = a.length;
        int rewritablePart = length - index;
        if (rewritablePart < collectionLength) {
            items = Arrays.copyOf(items, collectionLength + index);
            length = collectionLength + index;
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(a, 0, items, index, collectionLength);
        ++modCount;
        return true;
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
    public Object get(int index) {
        checkIndexInBounds(index);
        return items[index];
    }

    @Override
    public Object set(int index, Object element) {
        checkIndexInBounds(index);
        T old = items[index];
        //noinspection unchecked
        items[index] = (T) element;
        ++modCount;
        return old;
    }

    @Override
    public void add(int index, Object element) {
        checkIndexInBounds(index);
        if (index == length) {
            add(element);
            return;
        }
        if (length >= items.length) {
            increaseCapacity();
        }
        System.arraycopy(items, index, items, index + 1, length - index);
        //noinspection unchecked
        items[index] = (T) element;
        ++modCount;
    }

    @Override
    public Object remove(int index) {
        if (index < 0 || index >= length) {
            throw new IllegalArgumentException("Индекс выходит за пределы списка.");
        } else {
            if (index < length - 1) {
                System.arraycopy(items, index + 1, items, index, length - index - 1);
            }
            --length;
            ++modCount;
            return items[index];
        }
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
        int lastIndex = -1;
        for (int i = 0; i < length; ++i) {
            if (o == null ? items[i] == null : items[i].equals(0)) {
                lastIndex = i;
            }
        }
        return lastIndex;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListItr(0);
    }

    @Override
    public ListIterator listIterator(int index) {
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
    public List subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Метод subList не определен.");
    }

    @Override
    public boolean retainAll(Collection c) {
        if (c == null) {
            return false;
        }

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
    public boolean removeAll(Collection c) {
        if (c == null) {
            return false;
        }

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
    public boolean containsAll(Collection c) {
        if (c == null) {
            return false;
        }

        for (Object aC : c) {
            if (!contains(aC)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Object[] toArray(Object[] a) {
        if (a == null) {
            throw new IllegalArgumentException("Массив не инициализирован.");
        }

        int aLength = a.length;
        if (aLength >= length) {
            System.arraycopy(items, 0, a, 0, aLength);
        } else {
            a = new Object[length];
            System.arraycopy(items, 0, a, 0, length);
        }
        return a;
    }

    public void ensureCapacity(int capacity) {
        if (items.length < capacity) {
            items = Arrays.copyOf(items, capacity);
        }
    }

    public void trimToSize() {
        if (items.length > length) {
            items = Arrays.copyOf(items, length);
        }
    }
}
