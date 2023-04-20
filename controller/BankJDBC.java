package banking.controller;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class BankJDBC {
    // instance variable
    private final SQLiteDataSource dataSource;

    public BankJDBC(String filename) {
        // establish url
        this.dataSource = new SQLiteDataSource();
        this.dataSource.setUrl("jdbc:sqlite:" + filename);
    }

}
