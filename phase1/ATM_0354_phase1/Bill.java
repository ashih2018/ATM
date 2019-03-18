package ATM_0354_phase1;

import ATM_0354_phase2.Transaction;

import java.math.BigDecimal;

public class Bill extends Transaction {
    public Bill (int accountIdFrom, int accountIdTo, BigDecimal value) {
        super(accountIdFrom, accountIdTo, value, true);
    }
}
