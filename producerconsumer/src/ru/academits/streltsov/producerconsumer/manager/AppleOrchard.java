package ru.academits.streltsov.producerconsumer.manager;

import ru.academits.streltsov.producerconsumer.task.ThiefTask;
import ru.academits.streltsov.producerconsumer.task.ApplePickerTask;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AppleOrchard {
    private static final int APPLE = 1;
    private final Queue<Integer> basket = new LinkedList<>();
    private final List<Thread> threadList = new ArrayList<>();
    private final int basketCapacity;
    private int numberOfApples;
    private final Object lock = new Object();

    public AppleOrchard(int numberOfApples, int basketCapacity, int applePickersNumber, int thievesNumber) {
        this.numberOfApples = numberOfApples;
        this.basketCapacity = basketCapacity;

        for (int i = 1; i <= applePickersNumber; ++i) {
            Runnable r = new ApplePickerTask(this, i);
            Thread t = new Thread(r);
            threadList.add(t);
            t.start();
        }
        for (int i = 1; i <= thievesNumber; ++i) {
            Runnable r = new ThiefTask(this, i);
            Thread t = new Thread(r);
            threadList.add(t);
            t.start();
        }
    }

    public void putAppleInBasket(int id) throws InterruptedException {
        synchronized (lock) {
            --numberOfApples;
            System.out.println("Сборщик яблок " + id + " сорвал в саду яблоко. В саду осталось яблок: " + numberOfApples);

            while (basket.size() >= basketCapacity) {
                lock.wait();
            }
            basket.add(APPLE);

            System.out.println("Сборщик яблок " + id + " положил в корзину яблоко.");
            System.out.println("Яблок в корзине: " + basket.size());
            if (numberOfApples <= 0) {
                stop();
            }
            lock.notifyAll();
        }
    }

    private void stop() {
        for (Thread thread : threadList) {
            thread.interrupt();
        }
    }

    public void stealAppleFromBasket(int id) throws InterruptedException {
        synchronized (lock) {
            while (basket.size() <= 0) {
                lock.wait();
            }

            basket.remove();
            System.out.println("Вор " + id + " украл яблоко.");
            System.out.println("Яблок в корзине: " + basket.size());
            lock.notifyAll();
        }
    }
}
