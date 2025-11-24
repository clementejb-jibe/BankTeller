package BankSystem.userInterface.impl;

import BankSystem.bank.controller.impl.BankOperation;
import BankSystem.bank.controller.impl.BankOperatorController;
import BankSystem.bank.controller.impl.UserLogin;
import BankSystem.bank.controller.impl.UserLoginImpl;
import BankSystem.database.DatabaseService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Interface implements InterfaceImpl {
    private final Scanner scan;
    private final BankOperatorController operateBank;
    private final DatabaseService database;
    private final UserLogin userLogin;


    final String loginBorder = "-".repeat(39);
    final String bankBorder = "-".repeat(42);

    // Login Menu Method
    public void getLoginMenu() {
        System.out.println(loginBorder);
        System.out.println("|              LOGIN                  |");
        System.out.println(loginBorder);
        System.out.println("""
                    1. Register
                    2. Login
                    3. All Accounts (\033[3mFor Debugging Only\033[0m)
                    4. Exit""");
        System.out.print("  Select: ");
    }

    // Choice of Functions Method
    public void bankMenuFunctions() {
        boolean loginRunning = true;

        while (loginRunning) {
            getLoginMenu(); // Method to show Login Menu

            // Options for Interface
            try {
                int choice = scan.nextInt();
                scan.nextLine();

                switch (choice) {
                    case 1 -> userLogin.createAccount();
                    case 2 -> {
                        if (userLogin.login()) {
                            operateBank.setCurrentUser(userLogin.loggedInUser());
                            loginRunning = false;
                            showMenuInterface();
                        }
                    }
                    case 3 -> System.out.println(database.checkAccounts());
                    case 4 -> {
                        System.out.println("Program exiting...");
                        System.exit(0);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
    public Interface(Scanner scan,
                     UserLogin userLogin,
                     BankOperatorController operateBank,
                     DatabaseService database) {

        this.scan = scan;
        this.userLogin = userLogin;
        this.operateBank = operateBank;
        this.database = database;
    }

    @Override
    public void showLoginInterface() {
        bankMenuFunctions();
    }

    @Override
    public void showMenuInterface() {
        boolean isRunning = true;
        int options;

        //Main output
        while (isRunning) {
            System.out.println(); // Space

            System.out.println(bankBorder);
            System.out.println("|              BANK ACCOUNT              |");
            System.out.println(bankBorder);
            System.out.println("""
                    1. Check Balance
                    2. Deposit
                    3. Withdraw
                    4. Show History
                    5. Sign Out
                    6. Exit""");
            System.out.print("  Select: ");

            //Options
            try {
                options = scan.nextInt();
                System.out.println();
                switch (options) {
                    case 1 -> operateBank.showBalance();//Show the user balance
                    case 2 -> operateBank.deposit();//Deposit
                    case 3 -> operateBank.withdraw();//Withdraw
                    case 4 -> database.Transaction();//Show the transaction history
                    case 5 -> {
                        System.out.println("Signing out...");
                        isRunning = false;
                        showLoginInterface();
                    }
                    case 6 -> {
                        System.out.println("Thank you! Exiting the program...");
                        isRunning = false;
                    }//This exit the program
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please  try again.");
                scan.nextLine();
            }
        }
    }
}

