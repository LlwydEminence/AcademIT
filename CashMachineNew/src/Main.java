import controller.Controller;
import gui.FrameView;
import model.CashMachine;

public class Main {
    public static void main(String[] args) {
        FrameView frameView = new FrameView();
        CashMachine cashMachine = new CashMachine();
        Controller controller = new Controller(cashMachine, frameView);

        frameView.addController(controller);
        frameView.startCashMachine();
    }
}
