package BankSystem;

import BankSystem.bank.controller.impl.BankOperatorController;
import BankSystem.bank.controller.impl.UserLogin;
import BankSystem.database.DatabaseService;
import BankSystem.service.DatabaseController;
import BankSystem.user.User;
import BankSystem.service.CreateAccountService;
import BankSystem.userInterface.impl.Interface;



import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);



        User user = new User(); // Account Holder

        DatabaseService  databaseService = new DatabaseService(); // Main Database object

        CreateAccountService createAccount = new CreateAccountService(user, scan,  databaseService); // Class for creating account

        DatabaseController databaseController = new DatabaseController(createAccount); // Database Service

        BankAccount bankAccount = new BankAccount(databaseService);

        UserLogin userLogin = new UserLogin(databaseController, databaseService,user,scan); // User login Class

        BankOperatorController bankOperatorController =  new BankOperatorController( databaseService, scan); // Bank Functions (Deposit and Withdraw)

        Interface showInterface = new Interface(scan, userLogin, bankOperatorController, databaseService); // User Main Interface



        showInterface.showLoginInterface(); // Method to Show the login interface
        showInterface.showMenuInterface(); // Method for Bank Menu



        scan.close();
        
        
    }


}