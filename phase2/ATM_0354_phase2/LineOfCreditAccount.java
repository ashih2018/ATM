package ATM_0354_phase2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class LineOfCreditAccount extends DebtAccount {

    public LineOfCreditAccount(String username, int id) {
        super(username, id);
    }
    public LineOfCreditAccount(String username, int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions){
        super(username, id, balance, dateOfCreation, transactions);
    }

    @Override
    public String toString() {
        return "Line of Credit Account: " + "\n" + super.toString();
    }
}
