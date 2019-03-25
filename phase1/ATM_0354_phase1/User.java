package ATM_0354_phase1;

import ATM_0354_phase2.Account;
import ATM_0354_phase2.AccountFactory;
import ATM_0354_phase2.AssetAccount;
import ATM_0354_phase2.ChequingAccount;
import ATM_0354_phase2.DebtAccount;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.MoneyTransferException;
import ATM_0354_phase2.Person;
import ATM_0354_phase2.Transaction;
import ATM_0354_phase2.Transfer;

import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;


public class User extends Person {

    private ArrayList<ATM_0354_phase2.Account> accounts;
    ATM_0354_phase2.AccountFactory accountFactory;
    private Date creationDate;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private ATM_0354_phase2.Account primaryAccount;

    public User(String username, String password) {
        super(username, password);
        accounts = new ArrayList<>();
        accountFactory = new AccountFactory();
        ATM_0354_phase2.Account account = accountFactory.createAccount(getUsername(), "CHEQUINGACCOUNT");
        creationDate = new Date();
        primaryAccount = account;
        accounts.add(account);
    }

    public void setPrimaryAccount(ATM_0354_phase2.Account primaryAccount) {
        this.primaryAccount = primaryAccount;
    }

    public String getCreationDate() {
        return dateFormat.format(creationDate);
    }

    public ATM_0354_phase2.Account getAccount(int accountId) {
        for (ATM_0354_phase2.Account account : this.accounts) {
            if (account.getId() == accountId) return account;
        } return null;
    }

    public int getNumAccounts(){
        return this.accounts.size();
    }

    public void addAccount(String accountType) {
        ATM_0354_phase2.Account account = accountFactory.createAccount(getUsername(), accountType);
        this.accounts.add(account);
    }
    void addAccount(String accountType, int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<ATM_0354_phase2.Transaction> transactions){
        ATM_0354_phase2.Account account = accountFactory.createAccount(getUsername(), accountType, id, balance, dateOfCreation, transactions);
        this.accounts.add(account);
    }

    public boolean setPrimary(int accountID) {
        for (ATM_0354_phase2.Account account: this.accounts) {
            if (account instanceof ChequingAccount && account.getId() == accountID) {
                primaryAccount = account;
                return true;
            }
        }
        return false;
    }

    public ATM_0354_phase2.Account getPrimaryAccount() {
        return primaryAccount;
    }

    public int getPrimaryAccountId () {
        return primaryAccount.getId();
    }

    public void sendTransaction(String toUsername, int fromAccountId, BigDecimal value) throws ATM_0354_phase2.MoneyTransferException {
        // User should never send a transaction with a negative value
        // Check if username exists
        if (ATM_0354_phase2.Main.atm.userHandler.usernameExists(toUsername)) {
            //Check if the specified account exists
            ATM_0354_phase2.Account account = getAccount(fromAccountId);
            if (account != null) {
                ATM_0354_phase2.User toUser = (ATM_0354_phase2.User) Main.atm.userHandler.getUser(toUsername);
                // TODO: use users default deposit id
                ATM_0354_phase2.Transfer transfer =
                        new Transfer(fromAccountId, toUser.getPrimaryAccountId(), this.getUsername(), toUsername, value);
                // Process transaction for sender first b/c most likely if a problem were to occur, it would be
                // from subtracting money from an account, not depositing money into an account
                account.sendTransfer(transfer);
                toUser.getPrimaryAccount().receiveTransfer(transfer);
            } else throw new ATM_0354_phase2.MoneyTransferException(fromAccountId + " is not an existing account id that this user has");
        } else throw new ATM_0354_phase2.MoneyTransferException(toUsername + " is not an existing username");
    }

    public String getSummary() {
        // Use a StringBuilder instead of String when we are mutating a string is better practice
        StringBuilder summary = new StringBuilder();
        for (ATM_0354_phase2.Account account: this.accounts) {
            summary.append(account.toString());
            summary.append("\n");
        }
        return summary.toString();
    }

    public ATM_0354_phase2.Transaction getLastTransaction(int accountId) {
        ATM_0354_phase2.Account account = this.getAccount(accountId);
        if (account == null) {
            System.out.println("Account with id " + accountId + " doesn't exist ");
            return null;
        } else {
            ATM_0354_phase2.Transaction transaction = account.getLastTransaction();
            if (transaction == null) {
                System.out.println("User could not get last transaction from account");
                return null;
            } return transaction;
        }
    }

    public boolean checkIfTransactionExists(Transaction transaction) {
        for (ATM_0354_phase2.Account account : accounts) {
            if (account.doesTransactionExist(transaction)) return true;
        } return false;
    }

    public LocalDateTime getAccountDate(int accountId) {
        ATM_0354_phase2.Account account = this.getAccount(accountId);
        if (account == null) {
            System.out.println("Account does not exist");
            return null;
        } return account.getDateOfCreation();
    }

    public BigDecimal getAccountTotal() {
        BigDecimal total = new BigDecimal(0);
        for (ATM_0354_phase2.Account account: this.accounts) {
            if (account instanceof AssetAccount) {
                total = total.add(account.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if (account instanceof DebtAccount) {
                total = total.subtract(account.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }
        return total;
    }

    public boolean verifyID(int id){
        for (ATM_0354_phase2.Account account : accounts){
            if(account.getId() == id){
                return true;
            }
        }
        return false;
    }

    public void defaultTransferIn(BigDecimal amount){
        try{
            getPrimaryAccount().transferMoneyIn(amount);
        }
        catch(MoneyTransferException e){
            System.out.println("Money transfer exception when transferring between users. \n" +
                    "Why can't I transfer into my default deposit account?!");
        }

    }

    /* Writes to account_creation_requests.txt
     */
    public void requestAccount(String accountType){
        String filePath = "phase1/ATM_0354_phase1/ATM_0354_phase2.Files/account_creation_requests.txt";
        if(!(new HashSet<>(Arrays.asList("credit card", "line of credit", "chequing", "savings")).contains(accountType))){
            System.out.println("Invalid account type for request!");
            return;
        }
        String accountRequest = this.getUsername() + "," + accountType + "," + dateFormat.toString();
        File file = new File(filePath);
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(accountRequest);
            writer.newLine();
            writer.close();
        }
        catch(IOException e){
            System.out.println(e.toString());
            System.out.println("IOException when requesting account, writing to txt file.");
        }

    }

    /*
        For storage in people.txt
     */
    public String writeUser(){
        StringBuilder str = new StringBuilder("User," + getUsername() + "," + getPassword() +
                "," + getPrimaryAccountId());
        for (ATM_0354_phase2.Account account : accounts){
            str.append(",");
            str.append(account.getClass().getSimpleName());
            str.append(",");
            str.append(account.getBalance());
            str.append(",");
            str.append(account.getDateOfCreation().toString());
        }
        return str.toString();
    }

    public void writeTransactions() {
        for (Account account : accounts){
            account.writeTransactions(this.getUsername());
        }
    }
}
