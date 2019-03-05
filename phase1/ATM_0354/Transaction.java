package ATM_0354;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime date;

    private String userFrom, userTo;
    private BigDecimal value;
    private boolean isBill;

    public Transaction(String userFrom, String userTo, BigDecimal value, boolean isBill) {
        this.date = LocalDateTime.now();
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.value = value;
        this.isBill = isBill;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    public boolean getIsBill() {
        return isBill;
    }

}
