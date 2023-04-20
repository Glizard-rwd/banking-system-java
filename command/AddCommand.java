package src.command;

import src.domain.account.Account;
import src.domain.account.AccountCreator;
import src.domain.BankData;

public class AddCommand implements Command {
    private BankData bd;
    public AddCommand(BankData bd) {
        this.bd = bd;
    }

    @Override
    public void execute() {
        Account a = new AccountCreator().createAccount();
        bd.addAccount(a);
        bd.showAccount(a);
    }
}
