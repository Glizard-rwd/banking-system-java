package banking.domain;

import banking.controller.Application;
import banking.domain.account.Account;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankData {
    private Map<String, Account> accounts;

    public BankData() {
        this.accounts = new HashMap<>();
    }

    public void addAccount(Account a) {
        // check if exist
        // if not add to account list
        if (this.accounts.containsKey(a.getCardNum())) {
            Application.BANK_VIEW.showCardExist();
        } else {
            this.accounts.put(a.getCardNum(), a);
            Application.BANK_VIEW.showCreateCard();
        }
    }

    public void showAccount(Account a) {
        Application.BANK_VIEW.showCardInfo(a.getCardNum(), a.getCardPIN());
    }


    public boolean checkAccount(String num, String pin) {
        // num and pin from user input
        // anyMatch: does the account match any account in the list
        boolean isExist = this.accounts.values().stream()
                        .anyMatch(acc -> num.equals(acc.getCardNum())
                                && pin.equals(acc.getCardPIN()));
        return isExist;
    }

    public Account findAccountByCard(String num, String pin) {
        return this.accounts.values().stream()
                .filter(account -> account.getCardNum().equals(num)
                        && account.getCardPIN().equals(pin))
                .findFirst().orElse(null);
    }

    public List<Account> displayAllAccounts() {
        return this.accounts.values().stream().toList();
    }



    @Override
    public String toString() {
        return "BankData{" +
                "accounts=" + accounts +
                '}';
    }


}
