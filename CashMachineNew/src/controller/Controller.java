package controller;

import common.View;
import model.CashMachine;

import javax.naming.OperationNotSupportedException;

public class Controller {
    private final CashMachine cashMachine;
    private final View view;

    public Controller(CashMachine cashMachine, View view) {
        this.cashMachine = cashMachine;
        this.view = view;
    }

    public void needDeposit(int requiredDenomination, int cashNumber) throws OperationNotSupportedException {
        view.reportForDeposit(cashMachine.deposit(requiredDenomination, cashNumber));
    }

    public void needWithdrawCash(int requiredAmount, int requiredDenomination) throws OperationNotSupportedException {
        view.reportForWithdrawCash(cashMachine.withdrawCash(requiredAmount, requiredDenomination));
    }

    public void needData() {
        view.displayStatus(cashMachine.getAmountOfMoney(), cashMachine.getAvailableNotesNumber(), cashMachine.getCash());
    }

    public Integer[] needDenominations() {
        return cashMachine.getDenominations();
    }
}
