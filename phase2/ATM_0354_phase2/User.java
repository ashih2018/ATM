package ATM_0354_phase2;

import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


public class User extends Person {
    private Map<Integer, Account> accounts;
    AccountFactory accountFactory;
    private Date creationDate;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Account primaryAccount;
    private int loanLimit;

    public User(String username, String password, String salt) {
        super(username, password, salt);
        accounts = new HashMap<>();
        accountFactory = new AccountFactory();
        Account account = accountFactory.createAccount(getUsername(), "CHEQUINGACCOUNT");
        creationDate = new Date();
        primaryAccount = account;
        accounts.put(0, account);
        this.loanLimit = 50000;
    }

    public String getCreationDate() {
        return dateFormat.format(creationDate);
    }

    public Account getAccount(int accountId) {
        return this.accounts.get(accountId);
    }

    public int getNumAccounts() {
        return this.accounts.size();
    }

    public int addAccount(Account account) {
        int id = accountFactory.nextAccountId + 1; //fix this
        account.setId(id);
        this.accounts.put(id, account);
        return id;
    }

    public int addAccount(String accountType) {
        Account account = accountFactory.createAccount(getUsername(), accountType);
        this.accounts.put(account.getId(), account);
        return account.getId();
    }

    void addAccount(String accountType, int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions) {
        Account account = accountFactory.createAccount(getUsername(), accountType, id, balance, dateOfCreation, transactions);
        this.accounts.put(id, account);
    }

    public boolean setPrimary(int accountID) {
        Account account = this.accounts.get(accountID);
        if (account instanceof ChequingAccount) {
            primaryAccount = account;
            return true;
        }
        return false;
    }

    public Account getPrimaryAccount() {
        return primaryAccount;
    }

    public int getPrimaryAccountId() {
        return primaryAccount.getId();
    }

    public String getSummary() {
        StringBuilder summary = new StringBuilder();
        for (Integer id : this.accounts.keySet()) {
            Account account = this.accounts.get(id);
            if (id == this.getPrimaryAccountId())
                summary.append("(Primary Account)\n");
            summary.append(account.toString());
            summary.append("\n");
        }
        return summary.toString();
    }

    public void removeAccount(int id) {
        this.accounts.remove(id);
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
            }
            return transaction;
        }
    }

    public boolean checkIfTransactionExists(Transaction transaction) {
        for (Integer id : accounts.keySet()) {
            if (accounts.get(id).doesTransactionExist(transaction)) return true;
        }
        return false;
    }

    public LocalDateTime getAccountDate(int accountId) {
        Account account = this.getAccount(accountId);
        if (account == null) {
            System.out.println("Account does not exist");
            return null;
        }
        return account.getDateOfCreation();
    }

    public BigDecimal getAccountTotal() {
        BigDecimal total = new BigDecimal(0);
        for (Integer id : this.accounts.keySet()) {
            Account account = this.accounts.get(id);
            if (account instanceof AssetAccount) {
                total = total.add(account.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if (account instanceof DebtAccount) {
                total = total.subtract(account.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }
        return total;
    }

    public boolean verifyID(int id) {
        return this.accounts.containsKey(id);
    }

    public void defaultTransferIn(BigDecimal amount) {
        try {
            getPrimaryAccount().transferMoneyIn(amount);
        } catch (MoneyTransferException e) {
            System.out.println("Money transfer exception when transferring between users. \n" +
                    "Why can't I transfer into my default deposit account?!");
        }

    }

    void requestAccount(String accountType) {
        String accountRequest = "Individual," + this.getUsername() + "," + accountType + "," + dateFormat.toString();
        writeLineToAccReq(accountRequest);
    }

    private void writeLineToAccReq(String accountRequest) {
        File file = new File("phase2/ATM_0354_phase2/Files/account_creation_requests.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(accountRequest);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println("IOException when requesting account, writing to txt file.");
        }
    }

    public void requestJointAccount(User otherUser, String accountType) {
        String accountRequest = String.join(",", "Joint", this.getUsername(), otherUser.getUsername(), accountType, dateFormat.toString());
        writeLineToAccReq(accountRequest);
    }

    /*
        For storage in people.txt
     */
    public String writeUser() {
        StringBuilder str = new StringBuilder("User," + getUsername() + "," + getHash() +
                "," + getSalt() + "," + getPrimaryAccountId());

        for (Integer id : accounts.keySet()) {
            Account account = this.accounts.get(id);
            str.append(String.join(",", "", account.getClass().getSimpleName(), account.getBalance().toString(), account.getDateOfCreation().toString()));
        }
        return str.toString();
    }

    void writeTransactions() {
        this.accounts.forEach((id, account) -> account.writeTransactions());
    }
}
