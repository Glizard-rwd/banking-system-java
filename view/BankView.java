package src.view;

import java.security.cert.Extension;
import java.sql.SQLException;
import java.util.Scanner;

public class BankView {
    private final Scanner scanner;
    public BankView() {
        this.scanner = new Scanner(System.in);
    }

    public void showMenuTask() {
        String prompt = "1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit";
        System.out.println(prompt);
    }

    public void showCardTask() {
        String prompt = "1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit";
        System.out.println(prompt);
    }

    public void showInvalidTask() {
        System.out.println("Invalid option!");
    }

    public String askUserInput() {
        return scanner.nextLine();
    }


    public void showCreateCard() {
        System.out.println("Your card has been created");
    }

    public void showCardExist() {
        System.out.println("Card has been added!");
    }

    public void showCardInfo(String num, String pin) {
        System.out.println("Your card number:\n" + num);
        System.out.println("Your card PIN:\n" + pin);

    }

    public void showBalance(int balance) {
        System.out.println("Balance: " + balance);
    }

    public void askEnterCard() {
        System.out.println("Enter your card number:");
    }

    public void askEnterPIN() {
        System.out.println("Enter your PIN:");
    }

    public void showWrongData() {
        System.out.println("Wrong card number or PIN!");
    }

    public void showLoggedIn() {
        System.out.println("You have successfully logged in!");
    }

    public void showLoggedOut() {
        System.out.println("You have successfully logged out!");
    }

    public void showExit() {
        System.out.println("Bye!");
    }


    public void createTableError(SQLException sqe) {
        System.out.println("Error creating table: " + sqe.getMessage());
    }

    public void findAccountError(SQLException sqe) {
        System.out.println("Error finding account: " + sqe.getMessage());
    }

    public void insertAccountError(SQLException sqe) {
        System.out.println("Error inserting account: " + sqe.getMessage());
    }

    public void sameAccountError() {
        System.out.println("You can't transfer money to the same account!");
    }

    public void luhnError() {
        System.out.println("Probably you made a mistake in the card number. Please try again!");
    }

    public void notExistError() {
        System.out.println("Such a card does not exist.");
    }

    public void notEnoughMoneyError() {
        System.out.println("Not enough money!");
    }

    public void askForTransfer() {
        System.out.println("Enter how much money you want to transfer:");
    }

    public void showCloseAccount() {
        System.out.println("The account has been closed!");
    }
    public void enterIncome() {
        System.out.println("Enter income:");
    }

    public void showIncomeAdded() {
        System.out.println("Income was added!");
    }
}
