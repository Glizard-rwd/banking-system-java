package src.controller.command;

import src.domain.BankDatabase;

public class CloseAccountCommand implements Command {
    private final BankDatabase bd;
    private final String cardNum;

    public CloseAccountCommand(BankDatabase bd, String cardNum) {
        this.bd = bd;
        this.cardNum = cardNum;
    }

    public void execute() {
        bd.deleteAccount(cardNum);
    }
}
