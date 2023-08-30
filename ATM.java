
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

    // creates a new account from a UserId & amount
    public void openAccount(String input, double amount) throws Exception {
        // hashes userID
        String userID = hashString(input);

        // if there already exists and account, throw error
        if (accounts.containsKey(userID)) {
            throw new Exception("User already exists");
        }

        // else creates an account
        accounts.put(userID, amount);

    }

}