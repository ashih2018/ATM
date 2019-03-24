package ATM_0354_phase2;

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
        this.setMinimumAllowedBalance(new BigDecimal(-100));
    }

    @Override
    public String toString() {
        return "Chequing Account: " + "\n" + super.toString();
    }
}
