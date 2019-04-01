package ATM_0354_phase2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CreditCardAccount extends DebtAccount {

    public CreditCardAccount(String username, int id) {
        super(username, id, new BigDecimal(1000));
        this.setTransferOut(false);
    }
    public CreditCardAccount(String username, int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions){
        super(username, id, balance, dateOfCreation, transactions, new BigDecimal(1000));
        this.setTransferOut(false);
    }

    @Override
    public String toString() {
        return "Credit Card Account " + "\n" + super.toString();
    }
    @Override
    public String getSummary(int id){
        return "Credit Card Account \n" + super.getSummary(id);
    }
}
