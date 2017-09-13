package ru.academits.streltsov.producerconsumer.task;

import ru.academits.streltsov.producerconsumer.manager.AppleOrchard;

public class ThiefTask implements Runnable {
    private static final int DELAY = 3000;
    private final AppleOrchard appleOrchard;
    private final int id;

    public ThiefTask(AppleOrchard appleOrchard, int id) {
        this.appleOrchard = appleOrchard;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(DELAY);
                appleOrchard.stealAppleFromBasket(id);
            }
        } catch (InterruptedException ignored) {
        }
    }
}
