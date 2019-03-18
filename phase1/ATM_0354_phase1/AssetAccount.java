package ATM_0354_phase1;

import ATM_0354_phase2.Account;
import ATM_0354_phase2.MoneyTransferException;
import ATM_0354_phase2.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class AssetAccount extends Account {

    private BigDecimal minimumAllowedBalance;

    public AssetAccount(int id) {
        super(id);
    }
    public AssetAccount(int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions){
        super(id, balance, dateOfCreation, transactions);
    }

    @Override
    public void transferMoneyOut(BigDecimal value) throws ATM_0354_phase2.MoneyTransferException {
        if (this.getBalance().signum() < 1) {
            // Can't withdraw if balance is 0 or negative
            throw new ATM_0354_phase2.MoneyTransferException("Can't withdraw because the current balance is " + this.getBalance());
        } else if (this.getBalance().subtract(value).compareTo(this.getMinimumAllowedBalance()) >= 0) {
            // Withdraw if balance is greater than the minimum allowed balance
            super.transferMoneyOut(value);
        } else {
            throw new MoneyTransferException(
                    "Can't withdraw because the value to withdraw (" + value + ") makes the account balance " +
                            "lower than the minimum allowed balance"
            );
        }
    }

    public BigDecimal getMinimumAllowedBalance() {
        return this.minimumAllowedBalance;
    }

    public void setMinimumAllowedBalance(BigDecimal minimumAllowedBalance) {
        this.minimumAllowedBalance = minimumAllowedBalance;
    }
}
