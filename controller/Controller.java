package src.controller;

import src.command.Command;
import src.domain.BankData;
import src.domain.BankView;

public class Controller {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void execute() {
        this.command.execute();
    }
}
