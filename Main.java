package src;

import src.controller.Application;
import src.domain.BankData;
import src.domain.BankView;
import src.domain.account.Account;
import src.domain.account.AccountCreator;

public class Main {
    public static void main(String[] args) {
        Application application = new Application();
        application.run();
    }

}