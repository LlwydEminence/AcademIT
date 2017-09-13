import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class AppleOrchard {
    private static final int APPLE = 1;
    private final Queue<Integer> basket = new LinkedList<>();
    private final List<Thread> threadList = new ArrayList<>();
    private final int basketCapacity;
    private int numberOfApples;

    AppleOrchard(int numberOfApples, int basketCapacity, int applePickersNumber, int thievesNumber) {
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

    synchronized void putApplesInBasket(int appleCount, int id) throws InterruptedException {
        while (appleCount > basketCapacity - basket.size()) {
            wait();
        }

        for (int i = 1; i <= appleCount; ++i) {
            basket.add(APPLE);
            --numberOfApples;
        }

        System.out.println("Сборщик яблок " + id + " положил в корзину " +
                appleCount + " яблок" + getEnding(appleCount) + ". В саду осталось яблок: " + numberOfApples);
        System.out.println("Яблок в корзине: " + basket.size());
        if (numberOfApples <= 0) {
            stop();
        }
        notifyAll();
    }

    private void stop() {
        for (Thread thread : threadList) {
            thread.interrupt();
        }
    }

    synchronized void stealAppleFromBasket(int id) throws InterruptedException {
        while (basket.size() <= 0) {
            wait();
        }

        basket.remove();
        System.out.println("Вор " + id + " украл яблоко.");
        System.out.println("Яблок в корзине: " + basket.size());
        notifyAll();
    }

    int getBasketCapacity() {
        return basketCapacity;
    }

    synchronized int getNumberOfApples() {
        return numberOfApples;
    }

    private String getEnding(int appleCount) {
        if (appleCount % 10 == 1 && appleCount != 11) {
            return "о";
        } else if ((appleCount % 10 == 2 || appleCount % 10 == 3 || appleCount % 10 == 4)
                && appleCount != 12 && appleCount != 13 && appleCount != 14) {
            return "а";
        } else {
            return "";
        }
    }
}
