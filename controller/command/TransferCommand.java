package src.controller.command;

import src.domain.BankDatabase;

public class TransferCommand implements Command {
    private final BankDatabase bd;
    private final String transferAccount;
    private final String receivedAccount;
    private final int amount;

    public TransferCommand(BankDatabase bd, String transferAccount, String receivedAccount, int amount) {
        this.bd = bd;
        this.transferAccount = transferAccount;
        this.receivedAccount = receivedAccount;
        this.amount = amount;
    }

    // todo: verification: need to separate to other class
    // check both accounts are not the same

    @Override
    public void execute() {
        // enter and verify account
        // enter and verify transfer
        // do transfer
        // show result
        this.bd.transferMoney(this.transferAccount, this.receivedAccount, this.amount);
    }
}
