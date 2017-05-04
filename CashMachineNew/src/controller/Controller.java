package controller;

import model.CashMachine;
import textui.ConsoleView;

import javax.naming.OperationNotSupportedException;

public class Controller {
    private final CashMachine cashMachine;
    private final ConsoleView consoleView;

    public Controller(CashMachine cashMachine, ConsoleView consoleView) {
        this.cashMachine = cashMachine;
        this.consoleView = consoleView;
    }

    public void needDeposit(int requiredDenomination, int cashNumber) throws OperationNotSupportedException {
        consoleView.reportForDeposit(cashMachine.deposit(requiredDenomination, cashNumber));
    }

    public void needWithdrawCash(int requiredAmount, int requiredDenomination) throws OperationNotSupportedException {
        consoleView.reportForWithdrawCash(cashMachine.withdrawCash(requiredAmount, requiredDenomination));
    }

    public void needData() {
        consoleView.displayStatus(cashMachine.getAmountOfMoney(), cashMachine.getCash());
    }
}
