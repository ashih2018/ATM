package ATM_0354_phase2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChequingAccount extends AssetAccount {

    public ChequingAccount(String username, int id) {
        super(username, id);
        this.setMinimumAllowedBalance(new BigDecimal(-100));
    }

    public ChequingAccount(String username, int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions){
        super(username, id, balance, dateOfCreation, transactions);
        this.setMinimumAllowedBalance(new BigDecimal(-100));
    }

    @Override
    public String toString() {
        return "Chequing Account " + "\n" + super.toString();
    }
}
