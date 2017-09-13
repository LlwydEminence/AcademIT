public class ThiefTask implements Runnable {
    private static final int DELAY = 2000;
    private final AppleOrchard appleOrchard;
    private final int id;

    ThiefTask(AppleOrchard appleOrchard, int id) {
        this.appleOrchard = appleOrchard;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep((int) (DELAY * Math.random()));
                appleOrchard.stealAppleFromBasket(id);
            }
        } catch (InterruptedException ignored) {
        }
    }
}
