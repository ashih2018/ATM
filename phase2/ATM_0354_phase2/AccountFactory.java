package ATM_0354_phase2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AccountFactory {

    int nextAccountId;

    public AccountFactory() {
        // Starts with -1 because createAccount() adds 1 first
        this.nextAccountId = -1;
    }

    public void setNextAccountId(int nextAccountId) {
        this.nextAccountId = nextAccountId;
    }

    public Account createAccount(String username, String accountType) throws IllegalArgumentException {
        this.nextAccountId++;
        switch (accountType) {
            case "CREDITCARDACCOUNT": return new CreditCardAccount(username, this.nextAccountId);
            case "LINEOFCREDITACCOUNT": return new LineOfCreditAccount(username, this.nextAccountId);
            case "CHEQUINGACCOUNT": return new ChequingAccount(username, this.nextAccountId);
            case "SAVINGSACCOUNT": return new SavingsAccount(username, this.nextAccountId);
            case "INVESTMENTACCOUNT": return new InvestmentAccount(username, this.nextAccountId);
            default: throw new IllegalArgumentException("No such account of type: " + accountType);
        }
    }


    /* Todo setup investment account

     */
    //for setup
    public Account createAccount(String username, String accountType, int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions){
        switch (accountType) {
            case "CreditCardAccount": return new CreditCardAccount(username, id, balance, dateOfCreation, transactions);
            case "LineOfCreditAccount": return new LineOfCreditAccount(username, id, balance, dateOfCreation, transactions);
            case "ChequingAccount": return new ChequingAccount(username, id, balance, dateOfCreation, transactions);
            case "SavingsAccount": return new SavingsAccount(username, id, balance, dateOfCreation, transactions);
            case "InvestmentAccount": return new InvestmentAccount(username, id, balance, dateOfCreation, transactions);
            default: throw new IllegalArgumentException("No such account of type: " + accountType);
        }
    }

}
