package src.domain;

interface ICardDAO {
    // idea: sql interface
    String createTableSQL();
    String insertSQL();
    String findAccountSQL();
    String findAccountByNumberSQL();
    String deleteAccountSQL();
}

public class CardDAO implements ICardDAO {
    // idea: separate the sql database from the java code

    private final String tableName = "card";

    @Override
    public String createTableSQL() {
        return "CREATE TABLE IF NOT EXISTS card("
                + "id INTEGER PRIMARY KEY,"
                + "number TEXT NOT NULL,"
                + "pin TEXT NOT NULL,"
                + "balance INTEGER DEFAULT 0)";
    }
    @Override
    public String insertSQL() {
        return "INSERT INTO " + tableName + "(number,pin,balance)"
                + "VALUES(?,?,?)";
    }

    @Override
    public String findAccountSQL() {
        return "SELECT * FROM " + tableName + " WHERE number=? AND pin=?";
    }
    @Override
    public String findAccountByNumberSQL() {
        return "SELECT * FROM " + tableName + " WHERE number=?";
    }

    public String updateMoney() {
        return "UPDATE " + tableName + " SET balance=? WHERE number=?";
    }

    @Override
    public String deleteAccountSQL() {
        return "DELETE FROM " + tableName + " WHERE number=?";
    }
}
