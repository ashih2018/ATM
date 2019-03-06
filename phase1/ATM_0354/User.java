package ATM_0354;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;


public class User extends Person {

    private ArrayList<Account> accounts;
    private AccountFactory accountFactory;
    private Date creationDate;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static ATM atm;

    public User(String username, String password) {
        super(username, password);
        accounts = new ArrayList<>();
        accountFactory = new AccountFactory();
        Account account = accountFactory.createAccount("CHEQUINGACCOUNT");
        creationDate = new Date();
    }

    public String getCreationDate() {
        return dateFormat.format(creationDate);
    }

    public Account getAccount(int accountId) {
        for (Account account : this.accounts) {
            if (account.getId() == accountId) return account;
        } return null;
    }

    public int getNumAccounts(){
        return this.accounts.size();
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public void sendTransaction(String toUsername, int fromAccountId, BigDecimal value) throws MoneyTransferException {
        // Check if username exists
        if (atm.userHandler.usernameExists(toUsername)) {
            //Check if the specified account exists
            Account account = getAccount(fromAccountId);
            if (account != null) {
                User toUser = (User) atm.userHandler.getUser(toUsername);
                // TODO: use users default deposit id
                Transaction transaction = new Transaction(fromAccountId, toUser.getPrimaryAccountId(), value, false);
                // Process transaction for sender first b/c most likely if a problem were to occur, it would be
                // from subtracting money from an account, not depositing money into an account
                account.processTransaction(transaction);
                toUser.receiveTransaction(transaction);
            } else throw new MoneyTransferException(fromAccountId + " is not an existing account id that this user has");
        } else throw new MoneyTransferException(toUsername + " is not an existing username");
    }

    public void receiveTransaction(Transaction transaction) throws MoneyTransferException {
        Account account = this.getPrimaryAccount();
        if (account != null) {
            account.processTransaction(transaction);
        } else System.out.println("Account to receive transaction was null"); // Should never happen
    }

    public int getPrimaryAccountId() {
        for (Account account : this.accounts) {
            if (account instanceof ChequingAccount) {
                if (((ChequingAccount) account).isPrimary()) return account.getId();
            }
        } return -99; // Should never return -99 b/c every User should have at least one ChequingAccount
    }

    private Account getPrimaryAccount() {
        for (Account account : this.accounts) {
            if (account instanceof ChequingAccount) {
                if (((ChequingAccount) account).isPrimary()) return account;
            }
        } return null; // Should never return null b/c every User should have at least one ChequingAccount
    }

    public String getSummary() {
        // Use a StringBuilder instead of String when we are mutating a string is better practice
        StringBuilder summary = new StringBuilder();
        for (Account account: this.accounts) {
            summary.append(account.toString());
            summary.append("\n");
        }
        return summary.toString();
    }

    public Transaction getLastTransaction(int accountId) {
        Account account = this.getAccount(accountId);
        if (account == null) {
            System.out.println("Account with id " + accountId + " doesn't exist ");
            return null;
        } else {
            Transaction transaction = account.getLastTransaction();
            if (transaction == null) {
                System.out.println("User could not get last transaction from account");
                return null;
            } return transaction;
        }
    }

    public LocalDateTime getAccountDate(int accountId) {
        Account account = this.getAccount(accountId);
        if (account == null) {
            System.out.println("Account does not exist");
            return null;
        } return account.getDateOfCreation();
    }

    public BigDecimal getAccountTotal() {
        BigDecimal total = new BigDecimal(0);
        for (Account account: this.accounts) {
            if (account instanceof AssetAccount) {
                total = total.add(account.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if (account instanceof DebtAccount) {
                total = total.subtract(account.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }
        return total;
    }
}
