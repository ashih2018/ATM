package ATM_0354;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Account {
    private ArrayList<Transaction> transactions;

    private int id;
    private BigDecimal balance;
    private LocalDateTime dateOfCreation;

    public Account(int id) {
        this.transactions = new ArrayList<>();
        this.balance = new BigDecimal(0);
        this.id = id;
        this.dateOfCreation = LocalDateTime.now();
    }

    public Transaction getLastTransaction() {
        return this.transactions.get(this.transactions.size() - 1);
    }

    public LocalDateTime getDateOfCreation() {
        return this.dateOfCreation;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    @Override
    public String toString() {
        return "Account ID: " + this.id + "\nAccount Balance: " + this.balance;
    }
}
