package src.controller.command;


import src.controller.Application;
import src.domain.BankDatabase;

public class AddIncomeCommand implements Command {
    private final BankDatabase bd;
    private final String cardNumber;
    private final int income;

    public AddIncomeCommand(BankDatabase bd, String cardNumber, int income) {
        this.bd = bd;
        this.cardNumber = cardNumber;
        this.income = income;
    }

    @Override
    public void execute() {
        this.bd.addIncome(cardNumber, income);
        Application.BANK_VIEW.showIncomeAdded();
    }
}
