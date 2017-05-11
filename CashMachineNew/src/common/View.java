package common;

import controller.Controller;
import model.Cash;

public interface View {
    void startCashMachine();
    void reportForDeposit(int amount);
    void reportForWithdrawCash(int amount);
    void displayStatus(int amount, Cash[] cash);
    void addController(Controller controller);
}
