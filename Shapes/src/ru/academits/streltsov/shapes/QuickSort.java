package ru.academits.streltsov.shapes;

import java.util.ArrayList;
import java.util.Comparator;

class QuickSort {
    static <T> void quickSort(ArrayList<T> list, int left, int right, Comparator<T> comparator) {
        if (left >= right) {
            return;
        }

        T pivot = list.get(left);
        int i = left;
        int j = right;
        while (i <= j) {
            while (comparator.compare(list.get(i), pivot) < 0) {
                ++i;
            }
            while (comparator.compare(list.get(j), pivot) > 0) {
                --j;
            }
            if (i <= j) {
                T temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
                ++i;
                --j;
            }
        }
        if (i < right) {
            quickSort(list, i, right, comparator);
        }
        if (j > left) {
            quickSort(list, left, j, comparator);
        }
    }
}
