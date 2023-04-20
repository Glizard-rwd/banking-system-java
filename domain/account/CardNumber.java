package src.domain.account;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
    - generate card number
    - BIN: 400000
    - Account identifier: random 10 digits
 */
public class CardNumber {
    private final String BIN_NUMBER = "400000";
    private final String accountIdentifier;

    public CardNumber() {
        this.accountIdentifier = generateCardNum();
    }

    // function to generate cardNumber: unique + 16 digits
    private String generateCardNum() {
        SecureRandom secureRandom = new SecureRandom(); // declare secure random
        String cardNum;
        // generate cardNum until satisfy luhn algorithms
        do {
            cardNum = BIN_NUMBER + secureRandom.ints(10, 0, 10)
                    .mapToObj(Integer::toString)
                    .collect(Collectors.joining());
        } while (!luhn(cardNum));
        return cardNum;
    }

    // I don't want to use in other class
    private boolean luhn(String cardNumber) {
        int sumDigits = 0;
        boolean isDouble = false;
        for (int i = cardNumber.length()-1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            if (isDouble) {
                digit = doubleAndSubtract(digit);
            }
            sumDigits += digit;
            isDouble = !isDouble; // true to false or false to true;
        }

         // return if sum is divisible by 10;
        return sumDigits % 10 == 0;
    }

    private int doubleAndSubtract(int digit) {
        int d = digit * 2;
        return (d>9)? d-9: d;
    }

    //
    @Override
    public String toString() {
        return this.accountIdentifier;
    }
}
