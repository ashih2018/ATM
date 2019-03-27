package ATM_0354_phase2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class DebtAccount extends Account {
    // Debt accounts display a POSITIVE balance when user OWES money and vice versa

    public DebtAccount(String username, int id) {
        super(username, id);
    }
    public DebtAccount(String username, int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions){
        super(username, id, balance, dateOfCreation, transactions);
    }
    public void decreaseDebt(BigDecimal value) {
        this.setBalance(this.getBalance().subtract(value.setScale(2, BigDecimal.ROUND_HALF_UP)));
    }

    @Override
    public String toString() {
        return "Account ID: " + this.getId() + "\nAmount Owed: " + this.getBalance();
    }
}
