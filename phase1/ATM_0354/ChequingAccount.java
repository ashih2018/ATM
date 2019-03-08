package ATM_0354;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChequingAccount extends AssetAccount {

    public ChequingAccount(int id) {
        super(id);
        this.setMinimumAllowedBalance(new BigDecimal(-100));
    }
    
    public ChequingAccount(int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions){
        super(id, balance, dateOfCreation, transactions);
        this.primary = false;
        this.setMinimumAllowedBalance(new BigDecimal(-100));
    }
    public void setPrimary(boolean primary) {
        this.primary = primary;
    }


}
