package BankSystem.service;

import BankSystem.database.DatabaseService;
import BankSystem.service.impl.AccountServiceImpl;
import BankSystem.user.User;
import java.util.Scanner;

public class CreateAccountService implements AccountServiceImpl {
    private final User user;
    private final Scanner scan;
    private final DatabaseService dbController; // Database injection to fetch the data

    public CreateAccountService(User user, Scanner scan, DatabaseService dbController) {
        this.user = user;
        this.scan = scan;
        this.dbController = dbController;
    }

    @Override
    public void setID(User user) {
        Long newId = dbController.generatedId();
        this.user.setId(newId);
        System.out.println("Your Generated ID: " + newId);
    } // Automatically set ID for new users

    public void setPassword() {
        boolean isPasswordInvalid = true;

        while (isPasswordInvalid) {
            System.out.print("Create User Password: ");
            String enteredPassword = scan.nextLine();
            user.setPassword(enteredPassword);
            if (isPasswordValid(enteredPassword)) { // The method ->isPasswordValid<- Checks the entered password if valid
                isPasswordInvalid = false;
                System.out.println("Account Created Successfully");
                dbController.addAccount(user); // Put the id and password to Map as database
            } else {
                System.out.println("Sorry. Password is too weak!");
            }
        }

    } // User must set its own password

    @Override
    public boolean isPasswordValid(String password) {
        return password.length() >= 6 &&
                password.matches(".*[a-zA-Z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[@#$&*!].*");
    } // Password Validation
}
