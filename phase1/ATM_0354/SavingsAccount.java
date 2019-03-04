package ATM_0354;

import java.math.BigDecimal;

public class SavingsAccount extends AssetAccount {

    private BigDecimal minimumAllowedBalance, interest;

    public SavingsAccount(int id) {
        super(id);
        this.minimumAllowedBalance = new BigDecimal(0);
        this.interest = new BigDecimal(0.001);
    }

    public void addInterest() {
        // TODO: Do we need to add this as a transaction???
        // TODO: ATM should add interest to each SavingsAccount on the 1st of every month?
        this.transferMoneyIn(this.getBalance().multiply(this.interest));
    }

}
