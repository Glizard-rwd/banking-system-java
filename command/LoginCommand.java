package src.command;

import src.controller.Application;
import src.domain.BankData;

public class LoginCommand implements Command {
    private BankData bd;
    private String cardNum;
    private String pin;

    public LoginCommand(BankData bd, String cardNum, String pin) {
        this.bd = bd;
        this.cardNum = cardNum;
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
