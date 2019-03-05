package ATM_0354;

import java.math.BigDecimal;

public class SavingsAccount extends AssetAccount {

    private BigDecimal interest;

    public SavingsAccount(int id) {
        super(id);
        this.setMinimumAllowedBalance(new BigDecimal(0));
        this.interest = new BigDecimal(0.001);
    }

    public void addInterest() throws MoneyTransferException{
        // TODO: Do we need to add this as a transaction???
        // TODO: ATM should add interest to each SavingsAccount on the 1st of every month?
        this.transferMoneyIn(this.getBalance().multiply(this.interest));
    }

}
