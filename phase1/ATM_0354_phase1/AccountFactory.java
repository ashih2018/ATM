package ATM_0354_phase1;

import ATM_0354_phase2.Account;
import ATM_0354_phase2.ChequingAccount;
import ATM_0354_phase2.CreditCardAccount;
import ATM_0354_phase2.LineOfCreditAccount;
import ATM_0354_phase2.SavingsAccount;
import ATM_0354_phase2.Transaction;

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

    public ATM_0354_phase2.Account createAccount(String accountType) throws IllegalArgumentException {
        this.nextAccountId++;
        switch (accountType) {
            case "CREDITCARDACCOUNT": return new ATM_0354_phase2.CreditCardAccount(this.nextAccountId);
            case "LINEOFCREDITACCOUNT": return new ATM_0354_phase2.LineOfCreditAccount(this.nextAccountId);
            case "CHEQUINGACCOUNT": return new ATM_0354_phase2.ChequingAccount(this.nextAccountId);
            case "SAVINGSACCOUNT": return new ATM_0354_phase2.SavingsAccount(this.nextAccountId);
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
