package banking;

import banking.controller.Application;
import banking.domain.BankData;
import banking.domain.BankView;
import banking.domain.account.Account;
import banking.domain.account.AccountCreator;

public class Main {
    public static void main(String[] args) {
        Application application = new Application();
        application.run();
    }

}