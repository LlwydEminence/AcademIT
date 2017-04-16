package ru.academits.streltsov.hashtable.main;

import ru.academits.streltsov.hashtable.HashTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        HashTable<Integer> hashTable = new HashTable<>();
        hashTable.add(0);
        hashTable.add(1);
        hashTable.add(100);
        hashTable.add(200);
        hashTable.add(17);
        hashTable.add(9);
        Iterator<Integer> iterator = hashTable.iterator();

        ArrayList<Integer> list = new ArrayList<>(10);
        for (int i = 0; i < 10; ++i) {
            list.add(i);
        }

        hashTable.retainAll(list);
        System.out.println(hashTable);
    }
}
