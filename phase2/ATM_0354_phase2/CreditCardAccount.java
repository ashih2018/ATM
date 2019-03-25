package ATM_0354_phase2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CreditCardAccount extends DebtAccount {

    public CreditCardAccount(String username, int id) {
        super(username, id);
        this.setTransferOut(false);
    }
    public CreditCardAccount(String username, int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions){
        super(username, id, balance, dateOfCreation, transactions);
        this.setTransferOut(false);
    }
    @Override
    public void increaseDebt(BigDecimal value) throws UnsupportedOperationException{
        // TODO: Remove increaseDebt from parent class and add it to children that need it i.e. LineOfCreditAccount
        throw new UnsupportedOperationException(
                "An ATM can't increase the debt on this class (" + this.getClass() + ")");
    }

    @Override
    public String toString() {
        return "Credit Card Account: " + "\n" + super.toString();
    }
}
