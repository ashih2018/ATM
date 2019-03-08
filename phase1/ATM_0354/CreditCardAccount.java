package ATM_0354;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CreditCardAccount extends DebtAccount {

    public CreditCardAccount(int id) {
        super(id);
        this.setTransferOut(false);
    }
    public CreditCardAccount(int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions){
        super(id, balance, dateOfCreation, transactions);
        this.setTransferOut(false);
    }
    @Override
    public void increaseDebt(BigDecimal value) throws UnsupportedOperationException{
        // TODO: Remove increaseDebt from parent class and add it to children that need it i.e. LineOfCreditAccount
        throw new UnsupportedOperationException(
                "An ATM can't increase the debt on this class (" + this.getClass() + ")");
    }
}
