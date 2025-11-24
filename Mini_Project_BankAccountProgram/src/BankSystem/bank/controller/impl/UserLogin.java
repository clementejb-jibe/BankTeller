package BankSystem.bank.controller.impl;

import BankSystem.database.DatabaseService;
import BankSystem.service.DatabaseController;
import BankSystem.user.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserLogin implements UserLoginImpl {
    private final Scanner scan;
    private User user;
    private final DatabaseController databaseController;
    private final DatabaseService dbController;



    public UserLogin(DatabaseController databaseController,
                     DatabaseService userData,
                     User user,
                     Scanner scan) {
        this.scan = scan;
        this.databaseController = databaseController;
        this.dbController = userData;
        this.user = user;

    }

    @Override
    public void createAccount() {
        System.out.println("REGISTER:");
        databaseController.registerUser(user);
    }

    //Login Method
    @Override
    public boolean login() {
        try {
            System.out.println("LOGIN:");
            System.out.print("Enter Your ID: ");
            Long enterID = scan.nextLong();
            scan.nextLine();

            System.out.println("Enter Your Password: ");
            String enterPassword = scan.nextLine();

            String storedPassword = dbController.getPassword(enterID);

            if (storedPassword != null && storedPassword.equals(enterPassword)) {
                System.out.println("Successful login! Welcome!");
                user = new User();
                user.setId(enterID);
                user.setPassword(enterPassword);
                user.setBalance(dbController.userBalance.getOrDefault(enterID, 0.0));
                return true;
            } else {
                System.out.println("Invalid ID or Password!");
                return false;
            }
        } catch (InputMismatchException e) {
            System.out.println("Something went wrong.");
            scan.nextLine();
            return false;
        }
    }

    public User loggedInUser() {
        return user;
    }

}
