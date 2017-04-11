package ru.academits.streltsov.arraylist.main;

import ru.academits.streltsov.arraylist.ArrayList;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>(20);
        for (int i = 0; i < 20; ++i) {
            arrayList.add(i + 1);
        }

        ArrayList<Integer> arrayList1 = new ArrayList<>(22);
        for (int i = 0; i < 22; ++i) {
            arrayList1.add(100 - i);
        }

        System.out.println(Arrays.toString(arrayList.toArray()));
        System.out.println(Arrays.toString(arrayList1.toArray()));
        arrayList.addAll(19, arrayList1);
        System.out.println(Arrays.toString(arrayList.toArray()));
        //arrayList.retainAll(arrayList1);
        //arrayList.removeAll(arrayList1);
        if (arrayList.containsAll(arrayList1)) {
            System.out.println("Содержит все элементы.");
        }
        System.out.println(Arrays.toString(arrayList.toArray()));
        Integer[] a = new Integer[30];
        System.out.println(Arrays.toString(arrayList.toArray(a)));
    }
}
