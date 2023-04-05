package banking.controller;


import banking.domain.BankData;
import banking.domain.BankView;
import banking.command.*;



// enum: CREATE("1"), LOGIN("2"), EXIT("0")
// enum: BALANCE("1"), LOGOUT("2"), EXIT("0")


@SuppressWarnings("ALL")
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
            for (Menu m: Menu.values()) {
                if (m.value.equalsIgnoreCase(text)) {
                    return m;
                }
            }
            return null;
        }

    }
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
    }

    // access by class, only use by class, not instance
    private static final BankData BANK_DATA = new BankData();
    public static final BankView BANK_VIEW = new BankView(); // should be public?
    private static final Controller CONTROLLER = new Controller();

    public void run() {
        boolean isNotFinish = true;
        while (isNotFinish) {
            BANK_VIEW.showMenuTask();
            String userInput = BANK_VIEW.askUserInput();
            try {
                Menu menuOption = Menu.fromString(userInput);
                switch (menuOption) {
                    case CREATE -> createAction();
                    case LOGIN -> loginAction();
                    case EXIT -> isNotFinish = false;
                    default -> BANK_VIEW.showInvalidTask();
                }
            } catch (IllegalArgumentException e) {
                BANK_VIEW.showInvalidTask();
            }


        }
    }
    private void createAction() {
        Command createAccount = new AddCommand(BANK_DATA);
        CONTROLLER.setCommand(createAccount);
        CONTROLLER.execute(); // final
    }


    private void loginAction() {
        BANK_VIEW.askEnterCard();
        String checkCardNum = BANK_VIEW.askUserInput();
        BANK_VIEW.askEnterPIN();
        String checkPIN = BANK_VIEW.askUserInput();
        Command loginCommand = new LoginCommand(BANK_DATA, checkCardNum, checkPIN);
        CONTROLLER.setCommand(loginCommand);
        CONTROLLER.execute(); // final
        if (((LoginCommand) loginCommand).isLoggedIn()) {
            cardAction(checkCardNum, checkPIN);
        }
    }

    private void cardAction(String cardNum, String pin) {
        BANK_VIEW.showCardTask();
        boolean isNotExit = true;
        while (isNotExit) {
            Card cardOption = Card.fromString(BANK_VIEW.askUserInput());
            switch (cardOption) {
                case BALANCE:
                    CONTROLLER.setCommand(new BalanceCommand(BANK_DATA, cardNum, pin));
                    CONTROLLER.execute();
                    break;
                case LOGOUT:
                    run();
                    break;
                case EXIT:
                    isNotExit = false;
                    break;
                default:
                    BANK_VIEW.showInvalidTask();
                    break;
            }
        }
    }
}
