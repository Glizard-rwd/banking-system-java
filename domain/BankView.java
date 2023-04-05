package banking.domain;

import java.util.Scanner;

public class BankView {
    private Scanner scanner;
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
                "2. Log out\n" +
                "0. Exit";
        System.out.println(prompt);
    }

    public void showInvalidTask() {
        System.out.println("Invalid option!");
    }

    public String askUserInput() {
        String input = scanner.nextLine();
        return input;
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

    public void exit() {
        System.out.println("Bye!");
    }



}
