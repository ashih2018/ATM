package ATM_0354;

import java.math.BigDecimal;

public class Bill extends Transaction{
    public Bill (int accountIdFrom, int accountIdTo, BigDecimal value) {
        super(accountIdFrom, accountIdTo, value, true);
    }
}
