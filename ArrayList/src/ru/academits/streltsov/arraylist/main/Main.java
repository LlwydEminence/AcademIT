package ru.academits.streltsov.arraylist.main;

import ru.academits.streltsov.arraylist.ArrayList;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>(4);
        for (int i = 0; i < 4; ++i) {
            arrayList.add(i + 1);
        }

        java.util.ArrayList<Integer> arrayList1 = new java.util.ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            arrayList1.add(2);
        }
        System.out.println(arrayList1);
        System.out.println(arrayList);

        Integer[] a = new Integer[0];
        Integer[] x = arrayList.toArray(a);
        System.out.println(Arrays.toString(arrayList.toArray(a)));
    }
}
