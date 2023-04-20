package src.domain.account;

public class Account {
    private final CardNumber cardNum;
    private final PIN cardPIN;
    private int balance;

    public Account() {
        this.cardNum = new CardNumber();
        this.cardPIN = new PIN();
        this.balance = 0;
    }

    @Override
    public String toString() {
        return this.cardNum + " " + this.cardPIN;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getCardNum() {
        return cardNum.toString();
    }


    public String getCardPIN() {
        return cardPIN.toString();
    }


}
