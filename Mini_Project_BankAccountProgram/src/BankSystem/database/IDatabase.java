package BankSystem.database;

import BankSystem.user.User;

import java.util.Map;

public interface IDatabase {
    void addAccount(User user);

     String checkAccounts();

    void saveFile();

    void Transaction();
}
