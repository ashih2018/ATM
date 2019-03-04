package ATM_0354;

public class AccountFactory {

    private int nextAccountId;

    public AccountFactory() {
        // Starts with -1 because createAccount() adds 1 first
        this.nextAccountId = -1;
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

}
