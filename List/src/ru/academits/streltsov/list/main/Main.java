package ru.academits.streltsov.list.main;

import ru.academits.streltsov.list.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> singlyLinkedList = new SinglyLinkedList<>(1);
        SinglyLinkedList<Integer> singlyLinkedList1 = new SinglyLinkedList<>();
        try {
            for (int i = 1; i < 10; ++i) {
                singlyLinkedList.insert(i, i + 1);
            }

            singlyLinkedList.insertToTop(0);
            singlyLinkedList.insertToTop(-1);
            System.out.println("Исходный список: " + singlyLinkedList + "size =" + singlyLinkedList.getSize());

            singlyLinkedList1.insertToTop(1);
            singlyLinkedList1.insertToTop(0);
            System.out.println("Исходный список: " + singlyLinkedList1 + "size =" + singlyLinkedList1.getSize());
            singlyLinkedList.reverse();
            singlyLinkedList.insertToTop(1);
            System.out.println("Развернутый список: " + singlyLinkedList + "size =" + singlyLinkedList.getSize());
            System.out.println("Копия первого списка: " + singlyLinkedList.copy());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
