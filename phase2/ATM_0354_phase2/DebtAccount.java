package ATM_0354_phase2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class DebtAccount extends Account {
    private BigDecimal maxDebt;
    // Debt accounts display a POSITIVE balance when user OWES money and vice versa

    public DebtAccount(String username, int id, BigDecimal maxDebt) {
        super(username, id);
    }
    public DebtAccount(String username, int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions, BigDecimal maxDebt){
        super(username, id, balance, dateOfCreation, transactions);
    }
    //decreases debt
    @Override
    public void transferMoneyIn(BigDecimal value) throws MoneyTransferException {
        if (this.canTransferIn()) {
            setBalance(getBalance().subtract(value.setScale(2, BigDecimal.ROUND_HALF_UP)));
        } else throw new MoneyTransferException("Can't transfer money into this account");

    }

    //increases debt
    @Override
    public void transferMoneyOut(BigDecimal value) throws MoneyTransferException {
        // Note: Balance can be negative after using this method
        if (canTransferOut()) {
            if(!underMaxDebt(value)){
                System.out.println("Not enough funds to transfer.");
                return;
            }
            setBalance(getBalance().add(value.setScale(2, BigDecimal.ROUND_HALF_UP)));
        } else throw new MoneyTransferException("Can't transfer money out of this account");
    }

    @Override
    public boolean canTransferOut(){
        return super.canTransferOut();
    }

    private boolean underMaxDebt(BigDecimal value){
        return getBalance().add(value.setScale(2, BigDecimal.ROUND_HALF_UP)).compareTo(maxDebt) < 0;
    }

    public BigDecimal getMaxDebt() {
        return maxDebt;
    }

    public void setMaxDebt(BigDecimal maxDebt){
        this.maxDebt = maxDebt;
    }

    @Override
    public String toString() {
        return "Account ID: " + this.getId() + "\nAmount Owed: " + this.getBalance();
    }
}
