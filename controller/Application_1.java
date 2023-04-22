package src.controller;

import banking.command.AddCommand_1;
import banking.command.BalanceCommand_1;
import banking.command.Command_1;
import banking.command.LoginCommand_1;
import banking.domain.BankView;

public class Application_1 {
    enum Menu {
        CREATE("1"), LOGIN("2"), EXIT("0");
        private final String value;
        Menu(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Menu fromString(String text) {
            for (Menu m: Menu.values()) {
                if (m.value.equalsIgnoreCase(text)) {
                    return m;
                }
            }
            return null;
        }

    } // enum: CREATE("1"), LOGIN("2"), EXIT("0")
    enum Card {
        BALANCE("1"), LOGOUT("2"), EXIT("0");
        private final String value;
        Card(String value) {
            this.value = value;
        }

        public String getValue(){
            return value;
        }

        public static Card fromString(String text) {
            for (Card c: Card.values()) {
                if (c.value.equalsIgnoreCase(text)) {
                    return c;
                }
            }
            return null;
        }
    } // enum: BALANCE("1"), LOGOUT("2"), EXIT("0")

    private static final BankDatabase BANK_DATABASE = new BankDatabase(); // idea: cardDAO =))
    public static final BankView BANK_VIEW = new BankView(); // todo: should be public?
    private static final Controller_1 CONTROLLER = new Controller_1();


    // run
    public void run() {
        boolean isNotFinish = true;
        while (isNotFinish) {
            BANK_VIEW.showMenuTask();
            String userInput = BANK_VIEW.askUserInput();
            try {
                Menu menuOption = Menu.fromString(userInput);
                if (menuOption != null) {
                    switch (menuOption) {
                        case CREATE -> createAction();
                        case LOGIN -> loginAction();
                        case EXIT -> {
                            isNotFinish = false;
                        }

                        default -> BANK_VIEW.showInvalidTask();
                    }
                }
            } catch (IllegalArgumentException e) {
                BANK_VIEW.showInvalidTask();
            }
        }
    }

    private void createAction() {
        Command_1 createAccount = new AddCommand_1(BANK_DATABASE);
        CONTROLLER.setCommand(createAccount);
        CONTROLLER.execute();
    }

    private void loginAction() {
        BANK_VIEW.askEnterCard();
        String checkCardNum = BANK_VIEW.askUserInput();
        BANK_VIEW.askEnterPIN();
        String checkPIN = BANK_VIEW.askUserInput();
        LoginCommand_1 loginCommand = new LoginCommand_1(BANK_DATABASE, checkCardNum, checkPIN);
        CONTROLLER.setCommand(loginCommand);
        CONTROLLER.execute(); // final
        if (loginCommand.isLoggedIn()) {
            cardAction(checkCardNum, checkPIN);
        }
    }

    private void cardAction(String cardNum, String pin) {
        BANK_VIEW.showCardTask();
        while (true) {
            Card cardOption = Card.fromString(BANK_VIEW.askUserInput());
            if (cardOption != null) {
                switch (cardOption) {
                    case BALANCE -> {
                        CONTROLLER.setCommand(new BalanceCommand_1(BANK_DATABASE, cardNum, pin));
                        CONTROLLER.execute();
                    }
                    case LOGOUT -> run();
                    case EXIT -> {
                        BANK_DATABASE.close(); // Close the database connection
                        BANK_VIEW.showExit();
                        System.exit(0); // Stop the program execution
                    }
                    default -> BANK_VIEW.showInvalidTask();
                }
            }
        }

    }
}
