package BankSystem.bank.controller.impl;

import BankSystem.BankAccount;
import BankSystem.database.DatabaseService;
import BankSystem.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;


public class BankOperatorController implements BankOperation{
    private final Scanner scan;
    //private Double balance = 0.0;
    private final DatabaseService dbController;
    private final String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    private User currentUser;

    public BankOperatorController(DatabaseService dbController, Scanner scan) {
        this.dbController = dbController;

        this.scan = scan;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }


    @Override
    public double deposit() {

        Double depositAmount;

        System.out.print("Enter an amount to be deposited: ");

        try {
            depositAmount = scan.nextDouble();
            if (depositAmount > 0) {
                currentUser.setBalance(currentUser.getBalance() + depositAmount);
                System.out.printf("+ ₱%.2f Deposited.%n\n", depositAmount);
                showBalance();


                String depositFormat = "+ ₱" + depositAmount + " | Date: " + getDate();
                dbController.saveDepo(depositFormat); //Store the data to LIST with date/time
                dbController.userBalance.put(currentUser.getId(), currentUser.getBalance());

            } else {
                System.out.println("The amount should not be negative.");
            }
        } catch (InputMismatchException e) {
            System.out.println("The input should be a number. Please try again!");
            scan.next();
        }
        return currentUser.getBalance();
    }

    @Override
    public double withdraw() {
        double withdrawAmount;
        System.out.print("Enter an amount to withdraw: ");

        try {
            withdrawAmount = scan.nextDouble();
            if (withdrawAmount > 0 && withdrawAmount <= currentUser.getBalance()) {
                currentUser.setBalance(currentUser.getBalance() - withdrawAmount);
                System.out.printf("- ₱%.2f Withdraw.%n\n", withdrawAmount);
                showBalance();

                String withdrawFormat = "- ₱" + withdrawAmount + " | Date: " + getDate();
                dbController.saveWithdraw(withdrawFormat); //Store the data to LIST with date/time
            } else if (withdrawAmount <= 0) {
                System.out.println("The amount should not be negative.");
            } else {
                System.out.println("Insufficient balance");
            }
        } catch (InputMismatchException e) {
            System.out.println("The input should be a number. Please try again!");
            scan.next();
        }
        return currentUser.getBalance();
    }

    //Getter
    /*
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
    */

    public void showBalance() {
        if(currentUser == null) {
            System.out.println("No user has been logged in.");
            return;
        }
        System.out.printf("Your balance is: ₱%.2f%n", currentUser.getBalance());
    }

    public String getDate() {
        return date;
    }
}
