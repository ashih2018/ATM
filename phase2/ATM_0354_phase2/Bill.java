package ATM_0354_phase2;

import java.math.BigDecimal;

public class Bill extends Transaction {
    public Bill (int accountIdFrom, int accountIdTo, BigDecimal value) {
        super(accountIdFrom, accountIdTo, value, true);
    }
}
