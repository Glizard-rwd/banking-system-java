package src.command;

import banking.controller.Application_1;
import banking.controller.BankDatabase;

public class LoginCommand_1 implements Command_1 {
    private BankDatabase bd;
    private final String cardNum;
    private String pin;

    public LoginCommand_1(BankDatabase bd, String num, String pin) {
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
            Application_1.BANK_VIEW.showWrongData();
        } else {
            Application_1.BANK_VIEW.showLoggedIn();
        }
    }
}
