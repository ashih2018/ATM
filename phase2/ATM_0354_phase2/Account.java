package ATM_0354_phase2;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Account {
    private ArrayList<Transaction> transactions;
    private int id;
    private BigDecimal balance;
    private LocalDateTime dateOfCreation;
    private boolean transferIn, transferOut;
    private String username;

    // TODO: ask if all accounts have a minimum balance; currently only ChequingAccount does

    public Account(String username, int id) {
        this.username = username;
        this.transactions = new ArrayList<>();
        this.balance = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.id = id;
        this.dateOfCreation = LocalDateTime.now();
        this.transferIn = true;
        this.transferOut = true;
    }
    public Account(String username, int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions){
        this.username = username;
        this.transactions = transactions;
        this.balance = balance;
        this.id = id;
        this.dateOfCreation = dateOfCreation;
        this.transferIn = true;
        this.transferOut = true;
    }

    public void transferMoneyIn(BigDecimal value) throws MoneyTransferException {
        if (this.canTransferIn()) {
            this.balance = this.balance.add(value.setScale(2, BigDecimal.ROUND_HALF_UP));
        } else throw new MoneyTransferException("Can't transfer money into this account");

    }

    public void transferMoneyOut(BigDecimal value) throws MoneyTransferException {
        // Note: Balance can be negative after using this method
        if (canTransferOut()) {
            this.balance = this.balance.subtract(value.setScale(2, BigDecimal.ROUND_HALF_UP));
        } else throw new MoneyTransferException("Can't transfer money out of this account");
    }

    public void forceTransferIn(BigDecimal value) {
        this.balance = this.balance.add(value.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public void forceTransferOut(BigDecimal value) {
        this.balance = this.balance.subtract(value.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public Transaction getLastTransaction() throws IndexOutOfBoundsException{
        try {
            return this.transactions.get(this.transactions.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Couldn't get last transaction!");
            return null;
        }
    }

    public boolean doesTransactionExist(Transaction transaction) {
        return transactions.contains(transaction);
    }

    public void deleteSpecificTransaction(Transaction transaction) {
        // Should only be used SOME times
        this.transactions.remove(transaction);
    }

    public LocalDateTime getDateOfCreation() {
        return this.dateOfCreation;
    }

    public BigDecimal getBalance() {
        return this.balance.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public int getId() {
        return this.id;
    }

    public Transaction undoTransaction() {
        return this.transactions.remove(this.transactions.size() - 1);
    }

    public boolean canTransferIn() {
        return transferIn;
    }

    public boolean canTransferOut() {
        return transferOut;
    }

    public void setTransferIn(boolean cantransferIn) {
        this.transferIn = cantransferIn;
    }

    public void setTransferOut(boolean cantransferOut) {
        this.transferOut = cantransferOut;
    }

    public void setBalance(BigDecimal newBalance) {
        this.balance = newBalance.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    @Override
    public String toString() {
        return "Account ID: " + this.id + "\nAccount Balance: " + this.balance + "\n";
    }

    public void writeTransactions(){
        try{
            String filepath = "phase2/ATM_0354_phase2/Files/transactions.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filepath), true));
            //TODO: Format???
            for (Transaction transaction : transactions){
                writer.write(username + "," + transaction.serialize());
                writer.newLine();
            }
            writer.close();
        } catch(IOException e){
            System.out.println(e.toString());
            System.out.println("IOException when writing transaction to transactions.txt");
        }
    }
}
