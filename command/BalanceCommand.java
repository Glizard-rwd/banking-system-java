package src.command;

import src.controller.Application;
import src.domain.BankData;

public class BalanceCommand implements Command{
    private BankData bd;
    private String cardNum;
    private String cardPin;

    public BalanceCommand(BankData bd, String cardNum, String cardPin) {
        this.bd = bd;
        this.cardNum = cardNum;
        this.cardPin = cardPin;
    }

    @Override
    public void execute() {
        int balance = bd.findAccountByCard(this.cardNum, this.cardPin).getBalance();
        Application.BANK_VIEW.showBalance(balance);
    }
}
