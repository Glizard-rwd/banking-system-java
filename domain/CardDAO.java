package src.domain;

import src.domain.account.Account;

interface ICardDAO {
    // idea: sql interface
    String tableNameSQL();
    String deleteTableSQL();
    String createTableSQL();
    String insertSQL();
    String findAccountSQL();
}

public class CardDAO implements ICardDAO {
    // idea: separate the sql database from the java code

    private final String tableName = "card";
    @Override
    public String tableNameSQL() {
        return tableName;
    }

    @Override
    public String deleteTableSQL() {
        return "DROP TABLE IF EXISTS " + tableName;
    }

    @Override
    public String createTableSQL() {
        return "CREATE TABLE " + tableName +
                "\n ("
                + "id INTEGER,\n"
                + "number TEXT,\n"
                + "pin  TEXT,\n"
                + "balance INTEGER DEFAULT 0"
                + ")";
    }
    @Override
    public String insertSQL() {
        return "INSERT INTO " + tableName + " (number,pin,balance) "
                + "VALUES(?,?,?)";
    }

    @Override
    public String findAccountSQL() {
        return "SELECT * FROM " + tableName + " WHERE number=? AND pin=?";
    }

    public String addIncome() {
        return "UPDATE " + tableName + " SET balance=? + ? WHERE number=?";
    }

    public String deleteAccountSQL() {
        return "DELETE FROM " + tableName + " WHERE number=?";
    }

}
