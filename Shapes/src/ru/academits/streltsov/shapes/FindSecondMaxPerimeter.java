package ru.academits.streltsov.shapes;

import java.util.ArrayList;
import java.util.Comparator;

public class FindSecondMaxPerimeter {
    public static <T> T findSecondMaxPerimeter(ArrayList<T> list, Comparator<T> comparator) {
        QuickSort.quickSort(list, 0, list.size() - 1, comparator);
        return list.get(list.size() - 2);
    }
}
