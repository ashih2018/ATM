package ATM_0354_phase1;

import ATM_0354_phase2.DebtAccount;
import ATM_0354_phase2.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class LineOfCreditAccount extends DebtAccount {

    public LineOfCreditAccount(int id) {
        super(id);
    }
    public LineOfCreditAccount(int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions){
        super(id, balance, dateOfCreation, transactions);
    }

    @Override
    public String toString() {
        return "Line of Credit Account: " + "\n" + super.toString();
    }
}
