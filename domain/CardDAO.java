package src.domain;

public class CardDAO {
    // idea: separate the sql database from the java code
    private static final String TABLE_NAME = "card";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME; // delete table command
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "  ("
            + " id INTEGER,"
            + " number TEXT,"
            + " pin TEXT,"
            + " balance INTEGER DEFAULT 0"
            + ")"; // add table command

    private static final String INSERT = "INSERT INTO " + TABLE_NAME + " (number, pin, balance) VALUES (?, ?, ?)"; // insert command
    private static final String FIND_BY_NUMBER = "SELECT * FROM " + TABLE_NAME + " WHERE number = ?"; // select command
}
