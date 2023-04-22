package src.command;

import banking.controller.Application_1;
import banking.controller.BankDatabase;

public class BalanceCommand_1 implements Command_1 {
    private BankDatabase bd;
    private String num;
    private String pin;

    public BalanceCommand_1(BankDatabase bd, String num, String pin) {
        this.bd = new BankDatabase();
        this.num = num;
        this.pin = pin;
    }

    public void execute() {
        int balance = bd.findAccount(this.num, this.pin).getBalance();
        Application_1.BANK_VIEW.showBalance(balance);
    }
}
