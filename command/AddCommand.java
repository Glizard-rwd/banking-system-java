package src.command;

import src.controller.BankDatabase;
import src.domain.account.Account;
import src.domain.account.AccountCreator;

public class AddCommand implements Command {
    private BankDatabase bd;
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
