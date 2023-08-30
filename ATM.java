
import java.util.*;

/**
 * NOTES:
 * Create ID: Hash Email & store as string
 */

public class ATM {
    // HashMap of Accounts; Key: Email, Value: ID
    HashMap<String, Double> accounts = new HashMap<String, Double>();

    // hashes String & returns as String
    public String hashString(String str) {
        return Integer.toString(str.hashCode());
    }

    public double checkBalance(String userID) throws Exception {
        if (!accounts.containsKey(userID)) {
            throw new Exception("Account does not exist");
        }

        return accounts.get(userID);
    }

    // creates a new account from a email & amount
    public void openAccount(String email, double amount) throws Exception {
        // hashes userID
        String userID = hashString(email);

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

}