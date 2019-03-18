package ATM_0354_phase1;

import ATM_0354_phase2.Transaction;

import java.math.BigDecimal;

public class Deposit extends Transaction {
    public Deposit (int accountIdFrom, int accountIdTo, BigDecimal value) {
        super(accountIdFrom, accountIdTo, value, false);
    }
}
