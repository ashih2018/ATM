package ATM_0354;

import java.math.BigDecimal;

public class ChequingAccount extends AssetAccount {

    public ChequingAccount(int id) {
        super(id);
        this.setMinimumAllowedBalance(new BigDecimal(-100));
    }


}
