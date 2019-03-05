package ATM_0354;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime date;
    private String userFrom, userTo;
    private BigDecimal value;

    public Transaction(String userFrom, String userTo, BigDecimal value) {
        this.date = LocalDateTime.now();
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.value = value;
    }

}
