
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
    private boolean accountExist(String userId) {
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
            // System.out.println(withdraw + "-" + amount);
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

    private void printAccounts() {
        for (Map.Entry<String, Double> account : accounts.entrySet()) {
            System.out.println(account.getKey() + " = " + account.getValue());
        }
        System.out.println("____________________________");
    }

    public static void main(String[] args) throws Exception {
        ATM atm = new ATM();

        // tests if hashmap empty

        // testing accounts
        String[] emails = { "0@ee.com", "1@ff.com", "2@gg.com", "3.hhcom" };
        Double[] balance = { 1.0, 100.0, 9029.31093, 0.0 };

        // add accounts
        for (int i = 0; i < emails.length; i++) {
            atm.openAccount(emails[i], balance[i]);
        }
        // test exception
        // atm.openAccount(emails[0], 0000);

        atm.printAccounts();

        // deposit
        atm.depositMoney(emails[1], 0);
        atm.depositMoney(emails[3], 5);
        // test exception
        // atm.depositMoney(emails[0], -1);

        atm.printAccounts();

        // withdraw
        atm.withdrawMoney(emails[2], 9029);
        atm.withdrawMoney(emails[1], 0);
        // test exception
        // atm.withdrawMoney(emails[0], 1);
        // atm.withdrawMoney(emails[2], -5);

        atm.printAccounts();

        // transfer money
        atm.transferMoney(emails[1], emails[2], 50);
        atm.transferMoney(emails[2], emails[3], 0);
        // test exception
        // atm.transferMoney(emails[0], emails[1], -1);
        // System.out.println(atm.transferMoney(emails[0], emails[3], 100000));

        atm.printAccounts();

        atm.audit();

        System.out.println(atm.checkAudit());

        System.out.println("________________________________________________________________________");
        ;
        /**
         * Copy pasted from official tester -->
         */

        ATM bank = new ATM();
        int workingFunctions = 0;

        try {
            bank.openAccount("user1@example.com", 1000);
            workingFunctions++;

            bank.openAccount("user2@example.com", 500);
            workingFunctions++;

            bank.depositMoney("user1@example.com", 200);
            workingFunctions++;

            bank.withdrawMoney("user2@example.com", 100);
            workingFunctions++;

            bank.transferMoney("user1@example.com", "user2@example.com", 150);
            workingFunctions++;

            System.out.println("Balance for user1@example.com: " + bank.checkBalance("user1@example.com")); // Should be
                                                                                                            // 1050.0
            workingFunctions++;

            System.out.println("Balance for user2@example.com: " + bank.checkBalance("user2@example.com")); // Should be
                                                                                                            // 550.0
            workingFunctions++;

            bank.audit();
            workingFunctions++;

            System.out.println("Audit completed successfully.");
            verifyAuditFile("AccountAudit.txt", 2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Valid Use Cases: " + workingFunctions);
        }
    }

    private boolean checkAudit() throws Exception {
        // reads & compares account audit to expected line-by-line
        BufferedReader buffReader = new BufferedReader(new FileReader("AccountAudit.txt"));

        String expected = "";
        String actual = "";
        for (Map.Entry<String, Double> account : accounts.entrySet()) {
            expected = (account.getKey() + " = " + account.getValue());
            actual = buffReader.readLine();
            if (!expected.equals(actual)) {
                System.out.println("expected:" + expected + " - " + "actual:" + actual);
                buffReader.close();
                return false;
            }
        }

        buffReader.close();

        return true;
    }

    /**
     * copy pasted from official tester
     */
    private static void verifyAuditFile(String fileName, int expectedEntries) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int entryCount = 0;
            while (reader.readLine() != null) {
                entryCount++;
            }
            if (entryCount == expectedEntries) {
                System.out.println("Audit file entries match expected count.");
            } else {
                System.out.println(
                        "Audit file entries" + entryCount + "do not match expected count" + expectedEntries + ".");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}