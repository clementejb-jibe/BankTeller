package BankSystem.service;


import BankSystem.service.impl.AccountServiceImpl;
import BankSystem.user.User;

public class DatabaseController {
    private final AccountServiceImpl createAccount;

     public DatabaseController(AccountServiceImpl createAccount){
         this.createAccount = createAccount;

    }

    public void registerUser(User user){
        createAccount.setID(user);
        createAccount.setPassword();
    }


}
