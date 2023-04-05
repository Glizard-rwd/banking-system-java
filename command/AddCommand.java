package banking.command;

import banking.domain.account.Account;
import banking.domain.account.AccountCreator;
import banking.domain.BankData;

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
