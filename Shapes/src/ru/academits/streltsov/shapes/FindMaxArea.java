package ru.academits.streltsov.shapes;

import java.util.ArrayList;
import java.util.Comparator;

public class FindMaxArea {
    public static <T> T findMaxArea(ArrayList<T> list, Comparator<T> comparator) {
        T max = list.get(0);
        for (int i = 1; i < list.size(); ++i) {
            if (comparator.compare(list.get(i), max) > 0) {
                max = list.get(i);
            }
        }
        return max;
    }
}
