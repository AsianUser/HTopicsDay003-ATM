
import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * NOTES:
 * Create ID: Hash Email & store as string
 */

public class ATM {
    // HashMap of Accounts; Key: Email, Value: ID
    HashMap<String, Double> accounts = new HashMap<String, Double>();

    // // hashes String & returns as String
    // public String hashString(String str) {
    // return Integer.toString(str.hashCode());
    // }

    // check if account exists
    public boolean accountExist(String userId) {
        return (accounts.containsKey(userId));
    }

    public double checkBalance(String userID) throws Exception {
        if (!accounts.containsKey(userID)) {
            throw new Exception("Account does not exist");
        }

        return accounts.get(userID);
    }

    // creates a new account from a email & amount
    public void openAccount(String userID, double amount) throws Exception {

        // if amount neg, throw error
        if (amount < 0) {
            throw new Exception("Invalid deposit: deposit < 0");
        }

        // if there already exists and account, throw error
        if (accounts.containsKey(userID)) {
            throw new Exception("User already exists");
        }

        // else creates an account
        accounts.put(userID, amount);

    }

    public void closeAccount(String userID) throws Exception {
        // get value given userID
        // will throw exception if no exist
        double value = checkBalance(userID);

        // if has balance
        if (value > 0) {
            throw new Exception("Balance > 0\nPls withdraw all before try again");
        }

        // delet
        accounts.remove(userID);
    }

    public double depositMoney(String userID, double deposit) throws Exception {

        // if amount neg, throw error
        if (deposit < 0) {
            throw new Exception("Invalid deposit: deposit < 0");
        }

        // if account no exist, throw error
        if (!accounts.containsKey(userID)) {
            throw new Exception("User does not exist");
        }

        // replace amount
        accounts.put(userID, accounts.get(userID) + deposit);

        return deposit;
    }

    public double withdrawMoney(String userID, double withdraw) throws Exception {

        // if amount neg, throw error
        if (withdraw < 0) {
            throw new Exception("Invalid withdraw: withdraw < 0");
        }

        // if account no exist, throw error
        if (!accounts.containsKey(userID)) {
            throw new Exception("User does not exist");
        }

        // check how much money remains
        double amount = accounts.get(userID);

        // if withdraw > amount, throw error
        if (withdraw > amount) {
            throw new Exception("Withdrawal amount exceeds balance");
        }

        // actually withdraw money
        accounts.put(userID, accounts.get(userID) - withdraw);

        return withdraw;
    }

    public boolean transferMoney(String fromAccount, String toAccount, double amount) throws Exception {

        // if amount neg, throw error
        if (amount < 0) {
            throw new Exception("Invalid amount: amount < 0");
        }

        // check if accounts exist before running
        if (!accountExist(fromAccount))
            return false;
        if (!accountExist(toAccount))
            return false;

        // withdraw
        double balanceFrom = accounts.get(fromAccount);

        // if withdraw > amount, throw error
        if (amount > balanceFrom) {
            return false;
        }

        // actually withdraw money
        accounts.put(fromAccount, accounts.get(fromAccount) - amount);

        // deposit
        accounts.put(toAccount, accounts.get(toAccount) + amount);

        return true;
    }

    public void audit() throws IOException {
        // delete file if exists
        Files.deleteIfExists(Paths.get("AccountAudit.txt"));
        // c:/Users/benja/Documents/VSCode/HTopics Repos&Notes/HTopics Day 003/ATM/

        // create file
        File auditFile = new File("AccountAudit.txt");
        auditFile.createNewFile();

        // begin print writer
        PrintWriter pw = new PrintWriter(auditFile);

        // print out
        for (Map.Entry<String, Double> account : accounts.entrySet()) {
            // Printing all elements of a Map
            pw.write(account.getKey() + " = " + account.getValue() + "\n");
            // System.out.println (account.getKey() + " = " + account.getValue());
        }

        // close
        pw.close();
    }


}