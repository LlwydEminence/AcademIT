public class ApplePickerTask implements Runnable {
    private static final int DELAY = 2000;
    private final AppleOrchard appleOrchard;
    private final int id;

    ApplePickerTask(AppleOrchard appleOrchard, int id) {
        this.appleOrchard = appleOrchard;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep((int) (DELAY * Math.random()));
                int maxAppleCount = appleOrchard.getBasketCapacity();
                int appleCount = (int) ((maxAppleCount - 1) * Math.random() + 1);
                int remainingApplesNumber = appleOrchard.getNumberOfApples();
                if (appleCount > remainingApplesNumber) {
                    appleCount = remainingApplesNumber;
                }
                appleOrchard.putApplesInBasket(appleCount, id);
            }
        } catch (InterruptedException ignored) {
        }
    }
}
