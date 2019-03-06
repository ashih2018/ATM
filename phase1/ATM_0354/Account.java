package ATM_0354;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Account {
    private ArrayList<Transaction> transactions;
    private int id;
    private BigDecimal balance;
    private LocalDateTime dateOfCreation;
    private boolean transferIn, transferOut;

    // TODO: ask if all accounts have a minimum balance; currently only ChequingAccount does

    public Account(int id) {
        this.transactions = new ArrayList<>();
        this.balance = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.id = id;
        this.dateOfCreation = LocalDateTime.now();
        this.transferIn = true;
        this.transferOut = true;
    }

    public void transferMoneyIn(BigDecimal value) throws MoneyTransferException {
        if (this.canTransferIn()) {
            this.balance = this.balance.add(value.setScale(2, BigDecimal.ROUND_HALF_UP));
        } else throw new MoneyTransferException("Can't transfer money into this account");

    }

    public void transferMoneyOut(BigDecimal value) throws MoneyTransferException{
        // Note: Balance can be negative after using this method
        if (canTransferOut()) {
            this.balance = this.balance.subtract(value.setScale(2, BigDecimal.ROUND_HALF_UP));
        } else throw new MoneyTransferException("Can't transfer money out of this account");
    }

    public void processTransaction(Transaction transaction) throws MoneyTransferException {
        // TODO: Some types of accounts process transactions differently like DebtAccount so account for that
        if (transaction.getAccountIdFrom() == this.id) {
            this.transferMoneyOut(transaction.getValue().setScale(2, BigDecimal.ROUND_HALF_UP));
            addTransaction(transaction);
        } else if (transaction.getAccountIdTo() == this.id) {
            this.transferMoneyIn(transaction.getValue().setScale(2, BigDecimal.ROUND_HALF_UP));
            addTransaction(transaction);
        } else {
            System.out.println("Could not process transaction for account with ID: " + this.id);
        }
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

    @Override
    public String toString() {
        return "Account ID: " + this.id + "\nAccount Balance: " + this.balance;
    }
}
