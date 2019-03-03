package ATM_0354;

import java.math.BigDecimal;
import java.util.ArrayList;

public abstract class Account {
    private ArrayList<Transaction> transactions;

    private int id;
    private BigDecimal balance;

    public Account() {
        this.transactions = new ArrayList<>();
        this.balance = new BigDecimal(0);
        // TODO: Set id and figure out a method to create unique ID's for new accounts
    }

    public String summary() {
        return "Account ID: " + this.id + "\nAccount Balance: " + this.balance;
    }

}
