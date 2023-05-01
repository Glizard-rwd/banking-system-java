package src.controller.command;

import src.domain.BankDatabase;
import src.domain.account.Account;
import src.domain.account.AccountCreator;

public class AddCommand implements Command {
    private final BankDatabase bd;

    public AddCommand(BankDatabase bd) {
        this.bd = bd;
    }

    @Override
    public void execute() {
        Account a = new AccountCreator().createAccount();
        bd.insertAccount(a);
        bd.showAccount(a);
    }
}
