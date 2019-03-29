package ATM_0354_phase2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SavingsAccount extends AssetAccount {

    private BigDecimal interest;

    public SavingsAccount(String username, int id) {
        super(username, id);
        this.setMinimumAllowedBalance(new BigDecimal(0));
        this.interest = new BigDecimal(0.001);
    }
    public SavingsAccount(String username, int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions){
        super(username, id, balance, dateOfCreation, transactions);
        this.setMinimumAllowedBalance(new BigDecimal(0));
        this.interest = new BigDecimal(0.001);
    }

    public void addInterest() {
        try {
            this.transferMoneyIn(this.getBalance().multiply(this.interest));
        }catch(MoneyTransferException e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Savings Account " + "\n" + super.toString();
    }

}
