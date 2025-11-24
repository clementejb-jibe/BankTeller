package BankSystem;

import BankSystem.database.DatabaseService;
import BankSystem.user.User;

public class BankAccount extends User {
    private final DatabaseService databaseService;

    public BankAccount(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void loadBalance(User user) {

    }
}
