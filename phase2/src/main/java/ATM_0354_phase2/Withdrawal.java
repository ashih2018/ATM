package ATM_0354_phase2;

import java.math.BigDecimal;

public class Withdrawal extends Transaction {
    public Withdrawal(int accountIdFrom, int accountIdTo, BigDecimal value) {
        super(accountIdFrom, accountIdTo, value, false);
    }
}
