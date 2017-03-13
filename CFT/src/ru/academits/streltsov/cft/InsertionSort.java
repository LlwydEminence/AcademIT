package ru.academits.streltsov.cft;

import java.util.ArrayList;
import java.util.Comparator;

public class InsertionSort {
    private static <T> void insert(ArrayList<T> list, int index, Comparator<T> comparator) {
        int requiredIndex = 0;
        int left = 0;
        int right = index - 1;
        T temp = list.get(index);

        for (int i = left; i <= right; ++i) {
            if (comparator.compare(temp, list.get(i)) <= 0) {
                requiredIndex = i;
                for (int j = index; j > requiredIndex; --j) {
                    list.set(j, list.get(j - 1));
                }
                break;
            }
        }

        list.set(requiredIndex, temp);
    }

    public static <T> void sort(ArrayList<T> list, Comparator<T> comparator) {
        for (int i = 1; i < list.size(); ++i) {
            if (comparator.compare(list.get(i),list.get(i - 1)) < 0) {
                insert(list, i, comparator);
            }
        }
    }

}
