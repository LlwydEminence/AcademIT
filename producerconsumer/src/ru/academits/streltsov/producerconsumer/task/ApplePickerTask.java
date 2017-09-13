package ru.academits.streltsov.producerconsumer.task;

import ru.academits.streltsov.producerconsumer.manager.AppleOrchard;

public class ApplePickerTask implements Runnable {
    private static final int DELAY = 1000;
    private final AppleOrchard appleOrchard;
    private final int id;

    public ApplePickerTask(AppleOrchard appleOrchard, int id) {
        this.appleOrchard = appleOrchard;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(DELAY );
                appleOrchard.putAppleInBasket(id);
            }
        } catch (InterruptedException ignored) {
        }
    }
}
