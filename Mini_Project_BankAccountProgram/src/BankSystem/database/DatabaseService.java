package BankSystem.database;

import BankSystem.user.User;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// Main Database
public class DatabaseService implements IDatabase {
    private Long nextID = 1L;
    private final Map<Long, String> userCredentials = new TreeMap<>(); // Stores the user's id and password
    protected final List<String> depo = new LinkedList<>(); // Stores the user's Deposit Transactions
    private final List<String> withd = new LinkedList<>(); // Stores the user's Withdraw Transactions
    public Map<Long, Double> userBalance = new TreeMap<>();

    public Long generatedId() {
        return nextID++;
    } // The System Generates ID for the new users

    @Override
    public void addAccount(User user) {
        userCredentials.put(user.getId(), user.getPassword());
        userBalance.put(user.getId(), user.getBalance());
    }

    public String checkAccounts() {
        /*Next Feature is to add validation for showing all accounts.
            -if Map is null, then print "No Account Registered".
            -if Map is not empty, then show all accounts.
        */


        if (userCredentials.isEmpty()) {
            return "No User Registered.";
        } // Return this if Map is empty.

        StringBuilder summaryOfUsers = new StringBuilder("Registered Accounts:\n");

        for (Map.Entry<Long, String> entry : userCredentials.entrySet()) {
            summaryOfUsers.append("ID: ");
            summaryOfUsers.append(entry.getKey());
            summaryOfUsers.append(" | Password: ");
            summaryOfUsers.append(entry.getValue());
            summaryOfUsers.append("\n");
        }
        return summaryOfUsers.toString();
    } // For Debugging Only

    public String getPassword(Long id) {
        return userCredentials.get(id);
    }

    @Override
    public void saveFile() {
        try {
            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            FileWriter writer = new FileWriter("Transaction History.txt", true);
            writer.write("Date: " + date + "\n" +
                    """
                            ==== Transaction History ====
                            [ Deposits ]
                            """);
            for (String deposit : depo) {
                writer.write(deposit + "\n");
            }
            writer.write("""
                    
                    [ Withdraws ]
                    """);
            for (String withdraw : withd) {
                writer.write(withdraw + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
    }

    @Override
    public void Transaction() {
        System.out.println("==== Transaction History ====");
        if (depo.isEmpty()) {
            System.out.println("No Record for Deposit");
        } else {
            System.out.println("[ DEPOSITS ]");
            for (String historyDeposit : depo) {
                System.out.println(historyDeposit);
            }
        }
        if (withd.isEmpty()) {
            System.out.println("No Record for Withdraw");
        } else {
            System.out.println("[ WITHDRAWS ]");
            for (String historyWithdraw : withd) {
                System.out.println(historyWithdraw);
            }
        }
        saveFile();
    }

    public void saveDepo(String deposit) {
        depo.add(deposit);
    }

    public void saveWithdraw(String withdraw) {
        withd.add(withdraw);
    }
}
