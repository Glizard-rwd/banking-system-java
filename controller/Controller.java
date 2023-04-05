package banking.controller;

import banking.command.Command;
import banking.domain.BankData;
import banking.domain.BankView;

public class Controller {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void execute() {
        this.command.execute();
    }
}
