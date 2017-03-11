package ru.academits.streltsov.cft;

import java.util.ArrayList;
import java.util.Comparator;

public class InsertionSort {
    private static <T> void insert(ArrayList<T> list, int index, Comparator<T> comparator) {
        int requiredIndex = 0;
        int left = 0;
        int right = index - 1;
        T temp = list.get(index);
        while (left <= right){
            int middle = (left + right) / 2;
            if (comparator.compare(temp, list.get(middle)) == 0 || (comparator.compare(temp, list.get(middle)) < 0 && (middle == 0 || comparator.compare(temp, list.get(middle - 1)) > 0))) {
                requiredIndex = middle;
                break;
            } else if (comparator.compare(temp, list.get(middle)) < 0) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        for (int i = index; i > requiredIndex; --i) {
            list.set(i, list.get(i - 1));
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
