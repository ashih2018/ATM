package ATM_0354;

import java.math.BigDecimal;

public class Cheque extends Transaction{
    public Cheque (int accountIdFrom, int accountIdTo, BigDecimal value) {
        super(accountIdFrom, accountIdTo, value, false);
    }
}
