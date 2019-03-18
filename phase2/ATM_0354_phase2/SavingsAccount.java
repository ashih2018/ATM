package ATM_0354_phase2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SavingsAccount extends AssetAccount {

    private BigDecimal interest;

    public SavingsAccount(int id) {
        super(id);
        this.setMinimumAllowedBalance(new BigDecimal(0));
        this.interest = new BigDecimal(0.001);
    }
    public SavingsAccount(int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions){
        super(id, balance, dateOfCreation, transactions);
        this.setMinimumAllowedBalance(new BigDecimal(0));
        this.interest = new BigDecimal(0.001);
    }

    public void addInterest() throws MoneyTransferException {
        // TODO: Do we need to add this as a transaction???
        // TODO: ATM should add interest to each SavingsAccount on the 1st of every month?
        this.transferMoneyIn(this.getBalance().multiply(this.interest));
    }

    @Override
    public String toString() {
        return "Savings Account: " + "\n" + super.toString();
    }

}
