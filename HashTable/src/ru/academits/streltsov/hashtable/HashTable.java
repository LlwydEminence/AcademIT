package ru.academits.streltsov.hashtable;

import java.util.*;

public class HashTable<T> implements Collection<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private int capacity;
    private LinkedList<T>[] lists;
    private int size;
    private int modCount;

    public HashTable() {
        capacity = DEFAULT_CAPACITY;
        //noinspection unchecked
        lists = new LinkedList[capacity];
        for (int i = 0; i < capacity; ++i) {
            lists[i] = new LinkedList<>();
        }
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
            for (Iterator<T> iterator = lists[i].iterator(); iterator.hasNext();) {
                stringBuilder.append(iterator.next());
                if (iterator.hasNext()) {
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
        int index = Math.abs(o.hashCode() % capacity);
        return contains(o, index);
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
        private int currentPosition;
        private int previousPosition = -1;
        private int expectedModCount = modCount;
        @SuppressWarnings("unchecked")
        private Iterator<T>[] iterators = new Iterator[capacity];
        private int numberOfViewedElements;
        private boolean calledNext;
        private T last;
        private int startSize = size;

        private Itr() {
            for (int i = 0; i < capacity; ++i) {
                iterators[i] = lists[i].iterator();
            }
        }
        @Override
        public boolean hasNext() {
            return numberOfViewedElements != startSize;
        }

        @Override
        public T next() {
            checkForComodification();

            for (int i = currentPosition; i < capacity; ++i) {
                if (iterators[i].hasNext()) {
                    ++numberOfViewedElements;
                    calledNext = true;
                    last = iterators[i].next();
                    previousPosition = -1;
                    return last;
                }
                previousPosition = currentPosition;
                ++currentPosition;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            if (!calledNext) {
                throw new IllegalStateException("Сначала нужно вызвать метод next");
            }

            checkForComodification();
            if (previousPosition == -1) {
                iterators[currentPosition].remove();
                ++expectedModCount;
                ++modCount;
                calledNext = false;
                --size;
            } else {
                iterators[previousPosition].remove();
                ++expectedModCount;
                ++modCount;
                calledNext = false;
                --size;
            }
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
        int index = Math.abs(t.hashCode() % capacity);
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
        int index = Math.abs(o.hashCode() % capacity);

        return remove(o, index);
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
