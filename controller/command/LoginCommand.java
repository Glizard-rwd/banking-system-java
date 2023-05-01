package src.controller.command;

import src.controller.Application;
import src.domain.BankDatabase;

public class LoginCommand implements Command {
    private final BankDatabase bd;
    private final String cardNum;
    private final String pin;

    public LoginCommand(BankDatabase bd, String num, String pin) {
        this.bd = bd;
        this.cardNum = num;
        this.pin = pin;
    }

    public boolean isLoggedIn() {
        return this.bd.checkAccount(this.cardNum, this.pin);
    }

    @Override
    public void execute() {
        if (!isLoggedIn()) {
            Application.BANK_VIEW.showWrongData();
        } else {
            Application.BANK_VIEW.showLoggedIn();
        }
    }
}
