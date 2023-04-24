package src.domain;

import src.controller.Application;
import src.domain.account.Account;
import src.view.BankView;

import java.sql.*;


public class BankDatabase {
    // idea: no instance variable - no-arg constructor
    // connect to database
    // connect(): return a connection
    private static final String DB_URL = "jdbc:sqlite:card.s3db";
    private CardDAO cardDAO;
    public BankDatabase() {
        this.cardDAO = new CardDAO();
        this.createTable();
    }
    private Connection connect() throws SQLException {
        //methods will be part of the same transaction
        // and will only be committed once all statements have been successfully executed.
        Connection conn = DriverManager.getConnection(DB_URL);
        conn.setAutoCommit(false);
        return conn;
    }

    // create tables to keep records
    public void createTable() {
        String tableSQL = this.cardDAO.createTableSQL();
        try (Connection conn = this.connect()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(tableSQL);
        } catch (SQLException sqe) {
            Application.BANK_VIEW.createTableError(sqe); // todo: add to bankview
        }
    }

    // insert record into database
    // idea: the account must be auto-generated then input to database
    public void insertAccount(Account a) {
        String insertSQL = cardDAO.insertSQL(); // todo: combine sql to one file
        try (Connection conn = this.connect();
             PreparedStatement pStatement = conn.prepareStatement(insertSQL)) {
            pStatement.setString(1, a.getCardNum()); // insert card number
            pStatement.setString(2, a.getCardPIN()); // insert card pin
            pStatement.setInt(3, a.getBalance()); // insert card balance
            pStatement.executeUpdate();
        } catch (SQLException sqe) {
            Application.BANK_VIEW.insertAccountError(sqe); // todo: add to bankview
        }
    }

    public Account findAccount(String cardNumber, String cardPin) {
        String findSQL = this.cardDAO.findAccountSQL(); // idea: combine sql to one file
        try (Connection conn = this.connect();
             PreparedStatement pStatement = conn.prepareStatement(findSQL)) {
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
            Application.BANK_VIEW.findAccountError(sqe); // todo: add to bankview
        }
        return null;
    }

    public boolean checkAccount(String num, String pin) {
        return findAccount(num, pin) != null;
    }

    public void showAccount(Account a) {
        Application.BANK_VIEW.showCardInfo(a.getCardNum(), a.getCardPIN());
    }

    public void addIncome(Account a, int income) {
        String addIncomeSQL = this.cardDAO.addMoney(); // idea: combine sql to one file
        try (Connection conn = this.connect();
             PreparedStatement pStatement = conn.prepareStatement(addIncomeSQL)) {
            pStatement.setInt(1, a.getBalance()); // first "?" = current account balance
            pStatement.setInt(2, income); // second "?" = income
            pStatement.setString(3, a.getCardNum()); // final "?" = card number
            pStatement.executeUpdate(); // execute the sql statement
            conn.commit(); // todo: why do we need this line
            a.setBalance(a.getBalance() + income); // update the balance of account a
        } catch (SQLException sqe) {
            Application.BANK_VIEW.insertIncomeError(sqe); // todo: add to bankview
        }

    }

    public void close() { //idea: close database
        try {
            DriverManager.getConnection(DB_URL + ";shutdown=true");
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // todo: add to bankview
        }
    }

}


