package ru.academits.streltsov.hashtable;

import java.util.*;

public class HashTable<T> implements Collection<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private int capacity;
    private LinkedList<T>[] lists;
    private int size;
    private int modCount;

    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    public HashTable(int capacity) {
        this.capacity = capacity;
        //noinspection unchecked
        lists = new LinkedList[capacity];
        for (int i = 0; i < capacity; ++i) {
            lists[i] = new LinkedList<>();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < lists.length; ++i) {
            stringBuilder.append("{");
            int size = lists[i].size();
            int j = 0;
            for (T t : lists[i]) {
                stringBuilder.append(t);
                ++j;
                if (j < size) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append("}");
            if (i == lists.length - 1) {
                stringBuilder.append("]");
            } else {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    private int getIndex(Object o) {
        return Math.abs(Objects.hashCode(o) % capacity);
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
        return contains(o, getIndex(o));
    }

    private boolean contains(Object o, int index) {
        //noinspection SuspiciousMethodCalls
        return lists[index].contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        private int currentIndex;
        private int currentPosition;
        private Iterator<T> iterator = lists[0].iterator();
        private int expectedModCount = modCount;
        private T lastElement;

        @Override
        public boolean hasNext() {
            return currentPosition != size;
        }

        @Override
        public T next() {
            checkForComodification();

            if (!iterator.hasNext()) {
                ++currentIndex;
            } else {
                lastElement = iterator.next();
                ++currentPosition;
                return lastElement;
            }

            for (int i = currentIndex; i < capacity; ++i) {
                iterator = lists[i].iterator();
                if (iterator.hasNext()) {
                    lastElement = iterator.next();
                    ++currentPosition;
                    return lastElement;
                }
                ++currentIndex;
            }

            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            checkForComodification();

            if (lastElement == null) {
                throw new IllegalStateException();
            }

            iterator.remove();
            ++expectedModCount;
            ++modCount;
            lastElement = null;
            --currentPosition;
            --size;
        }

        private void checkForComodification() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (LinkedList<T> list : lists) {
            for (T t : list) {
                array[i] = t;
                ++i;
            }
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
        int index = getIndex(t);
        return !contains(t, index) && add(t, index);
    }

    private boolean add(T t, int index) {
        if (lists[index].add(t)) {
            ++modCount;
            ++size;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean remove(Object o) {
        return remove(o, getIndex(o));
    }

    private boolean remove(Object o, int index) {
        //noinspection SuspiciousMethodCalls
        if (lists[index].remove(o)) {
            ++modCount;
            --size;
            return true;
        } else {
            return false;
        }
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
            add(aC);
        }
        return expectedModCount != modCount;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);

        int expectedModCount = modCount;
        for (Object aC : c) {
            remove(aC);
        }
        return expectedModCount != modCount;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);

        int expectedSize = size;
        for (Iterator<T> iterator = iterator(); iterator.hasNext();) {
            T next = iterator.next();
            if (!c.contains(next)) {
                iterator.remove();
            }
        }
        return expectedSize != size;
    }

    @Override
    public void clear() {
        ++modCount;

        for (int i = 0; i < capacity; ++i) {
            lists[i].clear();
        }

        size = 0;
    }
}
