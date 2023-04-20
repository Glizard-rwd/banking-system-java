package src.controller;

import src.domain.account.Account;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Statement;

public class BankJDBC {
    // instance variable
    private final SQLiteDataSource dataSource;

    public BankJDBC(String filename) {
        // establish url
        this.dataSource = new SQLiteDataSource();
        this.dataSource.setUrl("jdbc:sqlite:" + filename);
    }

    // create + connect database
    public void connect() {
        try (Connection con = dataSource.getConnection()) {
            if (con.isValid(5)) {
                System.out.println("Connection is valid!"); // BankView: valid connection
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
    }
    // create table

    public void createTable(String tableName) {
        try (Connection con = dataSource.getConnection()) {
            try(Statement s = con.createStatement()) {
                String tableSQL = "CREATE TABLE IF NOT EXISTS cards("
                        + "id INTEGER PRIMARY KEY"
                        + "number TEXT NOT NULL"
                        + "pin TEXT NOT NULL"
                        + "balance INTEGER DEFAULT 0"; // interface?
                s.executeUpdate(tableSQL);
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }

    }
    // import account data to table
    public void insertTable(Account a) {

    }
    // display account


}
