package ru.academits.streltsov.linkedlist.main;

import ru.academits.streltsov.linkedlist.LinkedList;

import java.util.Arrays;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.addFirst(3);
        linkedList.addLast(1);
        linkedList.addLast(2);
        linkedList.addLast(3);
        linkedList.addFirst(0);

        LinkedList<Integer> linkedList1 = new LinkedList<>();
        for (int i = 0; i < linkedList.size(); ++i) {
            linkedList1.add(100 - i);
        }
        linkedList.addAll(5, linkedList1);
        linkedList.retainAll(linkedList1);
        System.out.println(linkedList);
    }
}
