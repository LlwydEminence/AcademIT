package common;

import controller.Controller;
import model.Cash;

public interface View {
    void startCashMachine();
    void reportForDeposit(int amount);
    void reportForWithdrawCash(Cash cash);
    void displayStatus(int amount, int notesNumber, Cash[] cash);
    void addController(Controller controller);
}
