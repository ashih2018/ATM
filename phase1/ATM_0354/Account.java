package ATM_0354;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Account {
    private ArrayList<Transaction> transactions;

    private int id;
    private BigDecimal balance;
    private LocalDateTime dateOfCreation;

    // TODO: ask if all accounts have a minimum balance; currently only ChequingAccount does

    public Account(int id) {
        this.transactions = new ArrayList<>();
        this.balance = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.id = id;
        this.dateOfCreation = LocalDateTime.now();
    }

    public void transferMoneyIn(BigDecimal value) {
        this.balance = this.balance.add(value.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public void transferMoneyOut(BigDecimal value) {
        // Note: Balance can be negative after using this method
        this.balance = this.balance.subtract(value.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public Transaction getLastTransaction() {
        return this.transactions.get(this.transactions.size() - 1);
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

    @Override
    public String toString() {
        return "Account ID: " + this.id + "\nAccount Balance: " + this.balance;
    }
}
