package src.command;

import banking.controller.BankDatabase;
import banking.domain.account.Account;
import banking.domain.account.AccountCreator;

public class AddCommand_1 implements Command_1 {
    private BankDatabase bd;
    public AddCommand_1(BankDatabase bd) {
        this.bd = bd;
    }
    @Override
    public void execute() {
        Account a = new AccountCreator().createAccount();
        bd.insertAccount(a);
        bd.showAccount(a);
    }
}
