package src.domain.account;

// abstract class Account Factory: createAccount + createSpecificAccount
// public class Account Creator
public class AccountCreator {
    public final Account createAccount() {
        Account a = new Account();
        return a;
    }
}
