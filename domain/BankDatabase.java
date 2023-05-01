package src.domain;

import banking.domain.CardDAO;
import src.domain.account.Account;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

import static banking.controller.Application.BANK_VIEW;


public class BankDatabase {
    // idea: no instance variable - no-arg constructor
    // connect to database
    // connect(): return a connection
    private static final String DB_URL = "jdbc:sqlite:C:card.s3db";
    private final CardDAO cardDAO;
    public BankDatabase() {
        this.cardDAO = new CardDAO();
        this.createTable();
    }
    private Connection connect() throws SQLException {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(DB_URL);
        Connection con = dataSource.getConnection();
        if (!con.isValid(5)) {
            System.out.println("Connection is invalid."); // todo: add to bankview
        }
        return con;
    }

    // create tables to keep records
    public void createTable() {
        String tableSQL = this.cardDAO.createTableSQL();
        try (Connection conn = this.connect()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(tableSQL);
        } catch (SQLException sqe) {
            BANK_VIEW.createTableError();
        }
    }

    // insert record into database
    // idea: the account must be auto-generated then input to database
    public void insertAccount(Account a) {
        String insertSQL = cardDAO.insertSQL(); // todo: combine sql to one file
        Savepoint sp = null;
        try (Connection conn = this.connect();
             PreparedStatement pStatement = conn.prepareStatement(insertSQL)) {
            conn.setAutoCommit(false); // idea: set-autocommit onetime
            sp = conn.setSavepoint();
            pStatement.setString(1, a.getCardNum()); // insert card number
                pStatement.setString(2, a.getCardPIN()); // insert card pin
                pStatement.setInt(3, a.getBalance()); // insert card balance
                pStatement.executeUpdate();
                conn.commit(); // idea: connect commit: when sth alters the database
        } catch (SQLException sqe) {
            BANK_VIEW.insertAccountError();
        }
    }

    public Account findAccount(String cardNumber, String cardPin) {
        String findSQL = this.cardDAO.findAccountSQL(); // idea: combine sql to one file
        try(Connection conn = this.connect();
            PreparedStatement pStatement = conn.prepareStatement(findSQL)) {
            conn.setAutoCommit(false); // idea: set-autocommit onetime
            pStatement.setString(1, cardNumber); // set resultSet object index 1 as card number
            pStatement.setString(2, cardPin); // set resultSet object index 2 as card pin
            // idea: result set: try-catch block: don't require .close() method
            // generate result set object to retrieve data
            try (ResultSet res = pStatement.executeQuery()) {
                if (res.next()) {
                    String dbCardNum = res.getString("number"); // retrieve card num from res object
                    String dbCardPin = res.getString("pin"); // retrieve card pin from res object
                    int balance = res.getInt("balance"); // retrieve balance from res object
                    return new Account(dbCardNum, dbCardPin, balance);
                }
            }
        } catch (SQLException sqe) {
            BANK_VIEW.findAccountError(sqe); // todo: add to bankview
        }
        return null;
    }

    public boolean checkAccount(String num, String pin) {
        return findAccount(num, pin) != null;
    }

    public boolean checkCardNumExist(String num) {
        return findAccountByNumber(num) != null;
    }

    public Account findAccountByNumber(String num) {
        String findSQL = this.cardDAO.findAccountByNumberSQL(); // idea: combine sql to one file
        try (Connection conn = this.connect();
             PreparedStatement pStatement = conn.prepareStatement(findSQL)) {
            pStatement.setString(1, num); // set resultSet object index 1 as card number
            // idea: result set: try-catch block: don't require .close() method
            // generate result set object to retrieve data
            try (ResultSet res = pStatement.executeQuery()) {
                if (res.next()) {
                    String dbCardNum = res.getString("number"); // retrieve card num from res object
                    String dbCardPin = res.getString("pin"); // retrieve card pin from res object
                    int balance = res.getInt("balance"); // retrieve balance from res object
                    return new Account(dbCardNum, dbCardPin, balance);
                }
            }
        } catch (SQLException sqe) {
            BANK_VIEW.findAccountError(sqe); // todo: add to bankview
        }
        return null;
    }

    public void showAccount(Account a) {
        BANK_VIEW.showCardInfo(a.getCardNum(), a.getCardPIN());
    }

    public void addIncome(String cardNumber, int income) {
        String addIncomeSQL = this.cardDAO.updateMoney(); // idea: combine sql to one file
        try (Connection conn = this.connect();
             PreparedStatement pStatement = conn.prepareStatement(addIncomeSQL)) {
            conn.setAutoCommit(false); // idea: set-autocommit onetime
            Account a = findAccountByNumber(cardNumber);
            pStatement.setInt(1, a.getBalance()+income); // first "?" = income
            pStatement.setString(2, a.getCardNum()); // second "?" = card number
            pStatement.executeUpdate(); // execute the sql statement
            conn.commit(); // idea: connect commit: when sth alters the database
            a.setBalance(a.getBalance() + income); // update the balance of account a
        } catch (SQLException sqe) {
            BANK_VIEW.insertIncomeError(sqe); // todo: add to bankview
            sqe.printStackTrace();
        }
    }


    public void transferMoney(String senderCardNum, String receiverCardNum, int money) {
        try {
            String transfer = this.cardDAO.updateMoney();
            String received = this.cardDAO.updateMoney();
            Connection conn = this.connect();
            conn.setAutoCommit(false); // idea: set-autocommit onetime

            Savepoint savepoint1 = conn.setSavepoint(); // set a savepoint before executing updates

            try (PreparedStatement pA = conn.prepareStatement(transfer);
                 PreparedStatement pB = conn.prepareStatement(received)) {
                Account senderAccount = findAccountByNumber(senderCardNum);
                Account receiverAccount = findAccountByNumber(receiverCardNum);

                // set "?" for transfer account
                pA.setInt(1, senderAccount.withdraw(money));
                pA.setString(2, senderCardNum);
                // set "?" for received account
                pB.setInt(1, receiverAccount.deposit(money));
                pB.setString(2, receiverCardNum);

                pA.executeUpdate();
                pB.executeUpdate();

                // if we've reached this point, both updates have executed successfully
                conn.commit(); // idea: connect commit: when sth alters the database

                // transfer money from sender to receiver
                senderAccount.setBalance(senderAccount.getBalance() - money);
                receiverAccount.setBalance(receiverAccount.getBalance() + money);
            } catch (SQLException sqe) {
                conn.rollback(savepoint1); // rollback to the savepoint set before executing updates
                BANK_VIEW.transferMoneyError(sqe); // todo: add to bankview
            }
        } catch (SQLException sqe) {
            BANK_VIEW.transferMoneyError(sqe); // todo: add to bankview
        }
    }

    public void close() { //idea: close database
        try {
            DriverManager.getConnection(DB_URL + ";shutdown=true");
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // todo: add to bankview
        }
    }

    public void deleteAccount(String cardNumber) {
        String deleteSQL = this.cardDAO.deleteAccountSQL(); // idea: combine sql to one file
        try (Connection conn = this.connect();
             PreparedStatement pStatement = conn.prepareStatement(deleteSQL)) {
            conn.setAutoCommit(false); // idea: set-autocommit onetime
            pStatement.setString(1, cardNumber); // set resultSet object index 1 as card number
            pStatement.executeUpdate();
            conn.commit(); // idea: connect commit: when sth alters the database
        } catch (SQLException sqe) {
            BANK_VIEW.deleteAccountError(sqe); // todo: add to bankview
        }
    }
}


