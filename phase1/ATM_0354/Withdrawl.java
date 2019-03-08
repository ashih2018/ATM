package ATM_0354;

import java.math.BigDecimal;

public class Withdrawl extends Transaction{
    public Withdrawl (int accountIdFrom, int accountIdTo, BigDecimal value) {
        super(accountIdFrom, accountIdTo, value, false);
    }
}
