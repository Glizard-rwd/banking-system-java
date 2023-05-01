package src.controller;

import src.controller.command.Command;

public class Controller {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void execute() {
        this.command.execute();
    }
}
