package src.controller.command;

import src.controller.Application;
import src.domain.BankDatabase;

public class BalanceCommand implements Command {
    private final BankDatabase bd;
    private final String num;
    private final String pin;

    public BalanceCommand(BankDatabase bd, String num, String pin) {
        this.bd = new BankDatabase();
        this.num = num;
        this.pin = pin;
    }

    public void execute() {
        int balance = bd.findAccount(this.num, this.pin).getBalance();
        Application.BANK_VIEW.showBalance(balance);
    }
}
