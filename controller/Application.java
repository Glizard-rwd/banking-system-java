package src.controller;


import src.controller.command.*;
import src.domain.BankDatabase;
import src.domain.account.Account;
import src.view.BankView;

public class Application {
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
            for (Menu m : Menu.values()) {
                if (m.value.equalsIgnoreCase(text)) {
                    return m;
                }
            }
            return null;
        }

    } // enum: CREATE("1"), LOGIN("2"), EXIT("0")

    enum Card {
        BALANCE("1"),
        ADD_INCOME("2"),
        DO_TRANSFER("3"),
        CLOSE_ACCOUNT("4"),
        LOGOUT("5"),
        EXIT("0");
        private final String value;

        Card(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Card fromString(String text) {
            for (Card c : Card.values()) {
                if (c.value.equalsIgnoreCase(text)) {
                    return c;
                }
            }
            return null;
        }
    }
    public static final BankView BANK_VIEW = new BankView(); // todo: should be public?
    private static final BankDatabase BANK_DATABASE = new BankDatabase(); // idea: cardDAO =))
    private static final Controller CONTROLLER = new Controller();


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
                            BANK_DATABASE.close(); // Close the database connection
                            BANK_VIEW.showExit();
                            System.exit(0);
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
        Command createAccount = new AddCommand(BANK_DATABASE);
        CONTROLLER.setCommand(createAccount);
        CONTROLLER.execute();
    }

    private void loginAction() {
        BANK_VIEW.askEnterCard();
        String checkCardNum = BANK_VIEW.askUserInput();
        BANK_VIEW.askEnterPIN();
        String checkPIN = BANK_VIEW.askUserInput();
        LoginCommand loginCommand = new LoginCommand(BANK_DATABASE, checkCardNum, checkPIN);
        CONTROLLER.setCommand(loginCommand);
        CONTROLLER.execute(); // final
        if (loginCommand.isLoggedIn()) {
            cardAction(checkCardNum, checkPIN);
        }
    }

    private void cardAction(String cardNum, String pin) {
        while (true) {
            BANK_VIEW.showCardTask();
            Card cardOption = Card.fromString(BANK_VIEW.askUserInput());
            if (cardOption != null) {
                switch (cardOption) {
                    case BALANCE -> {
                        CONTROLLER.setCommand(new BalanceCommand(BANK_DATABASE, cardNum, pin));
                        CONTROLLER.execute();
                    }
                    case ADD_INCOME -> {
                        Application.BANK_VIEW.askEnterIncome();
                        int income = Integer.parseInt(Application.BANK_VIEW.askUserInput());
                        CONTROLLER.setCommand(new AddIncomeCommand(BANK_DATABASE, cardNum, income));
                        CONTROLLER.execute();
                    }

                    case DO_TRANSFER -> {
                        System.out.println("Transfer");
                        Application.BANK_VIEW.askEnterCard();
                        String receiverNum = Application.BANK_VIEW.askUserInput();
                        if (receiverNum.equals(cardNum)) {
                            BANK_VIEW.sameAccountError();
                        } else if (!Account.isCardValid(receiverNum)) {
                            BANK_VIEW.luhnError();
                        } else if (!BANK_DATABASE.checkCardNumExist(receiverNum)) {
                            BANK_VIEW.notExistError();
                        } else {
                            Account transfer = BANK_DATABASE.findAccountByNumber(cardNum);
                            BANK_VIEW.askForTransfer();
                            int amount = Integer.parseInt(BANK_VIEW.askUserInput());
                            if (!transfer.canTransfer(amount)) {
                                BANK_VIEW.notEnoughMoneyError();
                            } else {
                                CONTROLLER.setCommand(new TransferCommand(BANK_DATABASE, cardNum, receiverNum, amount));
                                CONTROLLER.execute();
                                BANK_VIEW.transferDone();
                            }
                        }
                    }

                    case CLOSE_ACCOUNT -> {
                        CONTROLLER.setCommand(new CloseAccountCommand(BANK_DATABASE, cardNum));
                        CONTROLLER.execute();
                        BANK_VIEW.showCloseAccount();
                        run();
                    }

                    case LOGOUT -> {
                        BANK_VIEW.showLoggedOut();
                        run();
                    }
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
