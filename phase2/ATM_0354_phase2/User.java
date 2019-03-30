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

    public BigDecimal maxAccountTotal(){
        BigDecimal max = new BigDecimal(-1);
        for(Account acc: this.accounts.values()){
            if(acc.getBalance().compareTo(max) > 0){
                max = acc.getBalance();
            }
        }
        return max;
    }
    public Account getAccount(int accountId) {
        Account account;
        try {
            account = this.accounts.get(accountId);
        } catch (NullPointerException e) {
            account = null;
        }
        return account;
    }

    public int getNumAccounts() {
        return this.accounts.size();
    }

    public int addAccount(Account account) {
        int id = ++accountFactory.nextAccountId; //fix this
        account.setId(id);
        this.accounts.put(id, account);
        return id;
    }

    public int addAccount(String accountType) {
        Account account = accountFactory.createAccount(getUsername(), accountType);
        System.out.println(account.getId());
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


    public LocalDateTime getAccountDate(int accountId) {
        Account account = this.getAccount(accountId);
        if (account == null) {
            System.out.println("Account does not exist");
            return null;
        }
        return account.getDateOfCreation();
    }

    public int getLoanLimit() {
        return loanLimit;
    }

    public void changeLoanLimit(int number) {
        this.loanLimit += number;
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

    public void payBill(String destination, Account account, BigDecimal amount) {
        new Bill(destination, account, amount).process();
    }

    void requestAccount(String accountType) {
        String accountRequest = "Individual," + this.getUsername() + "," + accountType + "," + this.getCreationDate();
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

    void requestJointAccount(User otherUser, String accountType) {
        String accountRequest = String.join(",", "Joint", this.getUsername(), otherUser.getUsername(), accountType, this.getCreationDate());
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

    void newMonth(int deltaMonths) {
        for (Account account : this.accounts.values()) {
            if (account instanceof SavingsAccount)
                for (int i = 0; i < deltaMonths; i++) ((SavingsAccount) account).addInterest();
            for (Transaction transaction : account.getTransactions())
                if (transaction instanceof Loan) {
                    Loan loan = ((Loan) transaction);
                    if (!loan.isPaid()) {
                        loan.changeMonth(deltaMonths);
                    }

                }
        }
    }
    ArrayList<Transaction> getTransactions(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Account account : this.accounts.values()) {
            transactions.addAll(account.getTransactions());
        }
        transactions.sort(Comparator.comparing(Transaction::getDate));
        Collections.reverse(transactions);
        return transactions;
    }
    public String transactionHistory(){
        StringBuilder out = new StringBuilder();
        ArrayList<Transaction> transactions = getTransactions();
        out.append(String.format("%d;", transactions.size()));
        for(int i=0; i<transactions.size(); i++) {
            out.append(String.format("%d: (%s) %s\n", i, transactions.get(i).getDate().toString(), transactions.get(i).view()));
        }
        return out.toString();
    }
    public String loanSummary() {
        ArrayList<Loan> loans = getLoans();
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < loans.size(); i++) {
            Loan loan = loans.get(i);
            out.append(i);
            out.append(": ");
            out.append(loan.view());
            out.append("\n");
        }
        out.insert(0, String.format("%d;", loans.size()));
        return out.toString();
    }

    public boolean payLoan(BigDecimal amount, int loanId) {
        return getLoans().get(loanId).pay(amount);
    }

    public ArrayList<Loan> getLoans() {
        ArrayList<Loan> loans = new ArrayList<>();
        for (Account account : this.accounts.values())
            for (Transaction transaction : account.getTransactions())
                if (transaction instanceof Loan && !((Loan) transaction).isPaid()) loans.add((Loan) transaction);
        loans.sort(Loan::compareTo);
        return loans;
    }

    void writeTransactions() {
        this.accounts.forEach((id, account) -> account.writeTransactions());
    }

    public boolean hasInvestmentAccount(){
        for(int i = 0; i < getNumAccounts(); i++){
            if(accounts.get(i) instanceof  InvestmentAccount){
                return true;
            }
        }
        return false;
    }

    public String getInvestmentPortfolio(){
        for(int i = 0; i < getNumAccounts(); i++){
            if(accounts.get(i) instanceof  InvestmentAccount){
                return ((InvestmentAccount) accounts.get(i)).getInvestmentPortfolio();
            }
        }
        return "No investment portfolio found.";
    }

    public void buyStock(String symbol, int quantity){
        for(int i = 0; i < getNumAccounts(); i++){
            if(accounts.get(i) instanceof  InvestmentAccount){
                ((InvestmentAccount) accounts.get(i)).buyStock(symbol, quantity);
            }
        }
    }

    public void sellStock(String symbol, int quantity){
        for(int i = 0; i < getNumAccounts(); i++){
            if(accounts.get(i) instanceof  InvestmentAccount){
                ((InvestmentAccount) accounts.get(i)).sellStock(symbol, quantity);
            }
        }
    }

    public void undoTransactions(int num) {
        ArrayList<Transaction> transactions = this.getTransactions();
        for(int i=0; i<num; i++){
            transactions.get(i).undo();
        }
    }
}
