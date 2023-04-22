package src.controller;

import banking.command.Command_1;

public class Controller_1 {
    private Command_1 command;

    public void setCommand(Command_1 command) {
        this.command = command;
    }

    public void execute() {
        this.command.execute();
    }
}
