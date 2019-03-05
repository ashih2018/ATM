package ATM_0354;

import java.math.BigDecimal;

public class CreditCardAccount extends DebtAccount {

    public CreditCardAccount(int id) {
        super(id);
    }

    @Override
    public void decreaseDebt(BigDecimal value) {
        super.decreaseDebt(value);
    }

    @Override
    public void increaseDebt(BigDecimal value) throws UnsupportedOperationException{
        // TODO: Remove increaseDebt from parent class and add it to children that need it i.e. LineOfCreditAccount
        throw new UnsupportedOperationException(
                "An ATM can't increase the debt on this class (" + this.getClass() + ")");
    }
}
