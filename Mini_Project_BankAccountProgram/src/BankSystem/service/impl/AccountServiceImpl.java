package BankSystem.service.impl;

import BankSystem.user.User;

public interface AccountServiceImpl {
     void setID(User user);
     void setPassword();
     boolean isPasswordValid(String password);
}
