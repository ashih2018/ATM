package ATM_0354_phase2;

import java.math.BigDecimal;

public class Deposit extends Transaction {
    public Deposit (int accountIdFrom, int accountIdTo, BigDecimal value) {
        super(accountIdFrom, accountIdTo, value, false);
    }
}
