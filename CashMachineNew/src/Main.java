import controller.Controller;
import gui.FrameView;
import model.CashMachine;
import textui.ConsoleView;

public class Main {
    public static void main(String[] args) {
        /*ConsoleView consoleView = new ConsoleView();
        CashMachine cashMachine = new CashMachine();
        Controller controller = new Controller(cashMachine, consoleView);

        consoleView.addController(controller);
        consoleView.startCashMachine();*/

        FrameView frameView = new FrameView();
        CashMachine cashMachine = new CashMachine();
        Controller controller = new Controller(cashMachine, frameView);

        frameView.addController(controller);
        frameView.startCashMachine();
    }
}
