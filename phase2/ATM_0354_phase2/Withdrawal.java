package ATM_0354_phase2;

import java.math.BigDecimal;

public class Withdrawal extends Transaction {
    public Withdrawal(Account accountFrom, int value) {
        super(accountFrom, null, new BigDecimal(value));
    }

    @Override
    public String serialize() {
        return this.getClass().getSimpleName()
                + "," + getAccountFrom().getUsername() + "," + getAccountFrom().getId()
                + "," + getValue()
                + "," + getDate();
    }

}
