package ATM_0354;

import java.math.BigDecimal;

public abstract class DebtAccount extends Account {
    // Debt accounts display a POSITIVE balance when user OWES money and vice versa

    public DebtAccount(int id) {
        super(id);
    }

    public void decreaseDebt(BigDecimal value) throws MoneyTransferException{
        this.transferMoneyOut(value);
    }

    public void increaseDebt(BigDecimal value) throws MoneyTransferException {
        this.transferMoneyIn(value);
    }

    // TODO: Change the way processTransaction() works !!!!

    @Override
    public String toString() {
        return "Account ID: " + this.getId() + "\nAmount Owed: " + this.getBalance();
    }
}
