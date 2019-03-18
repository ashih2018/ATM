package ATM_0354;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AccountFactory {

    private int nextAccountId;

    public AccountFactory() {
        // Starts with -1 because createAccount() adds 1 first
        this.nextAccountId = -1;
    }

    public void setNextAccountId(int nextAccountId) {
        this.nextAccountId = nextAccountId;
    }

    public Account createAccount(String accountType) throws IllegalArgumentException {
        this.nextAccountId++;
        switch (accountType) {
            case "CREDITCARDACCOUNT": return new CreditCardAccount(this.nextAccountId);
            case "LINEOFCREDITACCOUNT": return new LineOfCreditAccount(this.nextAccountId);
            case "CHEQUINGACCOUNT": return new ChequingAccount(this.nextAccountId);
            case "SAVINGSACCOUNT": return new SavingsAccount(this.nextAccountId);
            default: throw new IllegalArgumentException("No such account of type: " + accountType);
        }
    }

    //for setup
    Account createAccount(String accountType, int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions){
        switch (accountType) {
            case "CreditCardAccount": return new CreditCardAccount(id, balance, dateOfCreation, transactions);
            case "LineOfCreditAccount": return new LineOfCreditAccount(id, balance, dateOfCreation, transactions);
            case "ChequingAccount": return new ChequingAccount(id, balance, dateOfCreation, transactions);
            case "SavingsAccount": return new SavingsAccount(id, balance, dateOfCreation, transactions);
            default: throw new IllegalArgumentException("No such account of type: " + accountType);
        }
    }

}
