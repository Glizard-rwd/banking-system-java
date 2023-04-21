package src.controller;

import org.sqlite.SQLiteDataSource;
import src.domain.account.Account;

import java.sql.*;


public class BankDatabase {
    // idea: no instance variable - no-arg constructor
    // connect to database
    // connect(): return a connection
    public Connection connect() {
        String url = "jdbc:sqlite:db.s3db";
        Connection conn = null;
        try {
            SQLiteDataSource dataSource = new SQLiteDataSource();
            conn = dataSource.getConnection();
        } catch (SQLException sqe) {
            System.out.println(sqe.getMessage()); // todo: add to BANKVIEW
        }
        return conn;
    }

    // create tables to keep records
    public void createTable() {
        String tableSQL = "CREATE TABLE IF NOT EXISTS cards("
                + "id INTEGER PRIMARY KEY"
                + "number TEXT NOT NULL"
                + "pin TEXT NOT NULL"
                + "balance INTEGER DEFAULT 0"; // todo: combine sql to one file
        try (Connection conn = this.connect()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(tableSQL);
        } catch (SQLException sqe) {
            System.out.println(sqe.getMessage()); // todo: add to bankview
        }
    }

    // insert record into database
    // idea: the account must be auto-generated then input to database
    public void insertAccount(Account a) {
        String insertSQL = "INSERT INTO cards(id, number, pin, balance) VALUES(?,?,?)"; // todo: combine sql to one file
        try (Connection conn = this.connect();
             PreparedStatement pStatement = conn.prepareStatement(insertSQL)) {
            pStatement.setString(1, a.getCardNum()); // insert card number
            pStatement.setString(2, a.getCardPIN()); // insert card pin
            pStatement.setInt(3, a.getBalance()); // insert card balance
            pStatement.executeUpdate();
        } catch (SQLException sqe) {
            System.out.println(sqe.getMessage()); // todo: add to bankview
        }
    }

    public Account findAccount(String cardNumber, String cardPin) {
        String findSQL = "SELECT * FROM cards WHERE number=? AND pin=?"; // todo: combine sql to one file
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
            System.out.println(sqe.getMessage()); // todo: add to bankview
        }
        return null;
    }
}


