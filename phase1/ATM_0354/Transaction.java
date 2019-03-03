package ATM_0354;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private Date date;
    private int fromId, toId;
    private BigDecimal value;

    public Transaction(int fromId, int toId, BigDecimal value) {
        this.date = new Date();
        this.fromId = fromId;
        this.toId = toId;
        this.value = value;
    }

}
