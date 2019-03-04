package ATM_0354;

import java.math.BigDecimal;
import java.util.ArrayList;

public abstract class Account {
    private ArrayList<Transaction> transactions;

    private int id;
    private BigDecimal balance;

    public Account(int id) {
        this.transactions = new ArrayList<>();
        this.balance = new BigDecimal(0);
        this.id = id;
    }

    @Override
    public String toString() {
        return "Account ID: " + this.id + "\nAccount Balance: " + this.balance;
    }
}
