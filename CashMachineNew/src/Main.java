import controller.Controller;
import model.CashMachine;
import textui.ConsoleView;

public class Main {
    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView();
        CashMachine cashMachine = new CashMachine();
        Controller controller = new Controller(cashMachine, consoleView);

        consoleView.addController(controller);
        consoleView.startCashMachine();
    }
}
