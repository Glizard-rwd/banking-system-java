package src.domain.account;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.stream.Collectors;

public class Account {
    private final String cardNum;
    private final String cardPIN;
    private int balance;

    public Account() {
        this.cardNum = generateCardNum();
        this.cardPIN = generatePIN();
        this.balance = 0;
    }

    public Account(String cardNumber, String cardPIN, int balance) {
        this.cardNum = cardNumber;
        this.cardPIN = cardPIN;
        this.balance = balance;
    }

    private String generateCardNum() {
        SecureRandom secureRandom = new SecureRandom();
        String BIN_NUMBER = "400000";
        String cardNum;
        do {
            cardNum = BIN_NUMBER + secureRandom.ints(10, 0, 10)
                    .mapToObj(Integer::toString)
                    .collect(Collectors.joining());
        } while (!luhn(cardNum));
        return cardNum;
    }

    private static boolean luhn(String cardNumber) {
        int sumDigits = 0;
        boolean isDouble = false;
        for (int i = cardNumber.length()-1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            if (isDouble) {
                digit = doubleAndSubtract(digit);
            }
            sumDigits += digit;
            isDouble = !isDouble;
        }
        return sumDigits % 10 == 0;
    }

    // check card number is valid in luhn algorithm
    public static boolean isCardValid(String cardNumber) {
        return luhn(cardNumber);
    }

    private static int doubleAndSubtract(int digit) {
        int d = digit * 2;
        return (d>9)? d-9: d;
    }

    private String generatePIN() {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.ints(4, 0, 10)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    public String getCardNum() {
        return cardNum;
    }
    public String getCardPIN() {
        return cardPIN;
    }

    // deposit account
    public int deposit(int amount) {
        this.balance += amount;
        return this.balance;
    }

    // withdraw account
    public int withdraw(int amount) {
        this.balance -= amount;
        return this.balance;
    }

    // can transfer?
    public boolean canTransfer(int amount) {
        return this.balance >= amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Account other)) {
            return false;
        }
        return Objects.equals(this.cardNum, other.cardNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNum);
    }
}

