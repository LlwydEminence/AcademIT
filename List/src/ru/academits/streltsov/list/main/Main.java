package ru.academits.streltsov.list.main;

import ru.academits.streltsov.list.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> singlyLinkedList = new SinglyLinkedList<>(1);

        try {
            for (int i = 1; i < 10; ++i) {
                singlyLinkedList.insert(i, i + 1);
            }

            System.out.println("Исходный список: " + singlyLinkedList);
            singlyLinkedList.reverse();
            System.out.println("Развернутый список: " + singlyLinkedList);
            System.out.println("Копия первого списка: " + singlyLinkedList.copy());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
