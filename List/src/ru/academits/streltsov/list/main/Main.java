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
            System.out.println("Исходный список: " + singlyLinkedList);

            singlyLinkedList1.insertToTop(1);
            System.out.println("Исходный список: " + singlyLinkedList1);
            singlyLinkedList.reverse();
            System.out.println("Развернутый список: " + singlyLinkedList);
            System.out.println("Копия первого списка: " + singlyLinkedList.copy());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
