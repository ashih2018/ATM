package ATM_0354;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime date;

    private String userFrom, userTo;
    //TODO: do we need accnumberfrom and accnumberto?
//    private int accNumberFrom, accNumberTo;
    private BigDecimal value;
    private boolean isBill;

    public Transaction(String userFrom, String userTo, BigDecimal value, boolean isBill) {
        this.date = LocalDateTime.now();
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.value = value;
        this.isBill = isBill;
    }

    public boolean getIsBill() {
        return isBill;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    public BigDecimal getValue() {
        return value;
    }
}
