package ATM_0354_phase2;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Withdrawal extends Transaction {
    public Withdrawal(Account accountFrom, int value) {
        super(accountFrom, null, new BigDecimal(value), LocalDateTime.now());
    }

    Withdrawal(Account accountFrom, int value, LocalDateTime date) {
        super(accountFrom, null, new BigDecimal(value), date);
    }

    public void process() {
        if(Main.atm.cashHandler.withdrawCash(this.getValue().intValue())) super.process();
    }

    @Override
    public String serialize() {
        return String.join(",",
                this.getClass().getSimpleName(), ((Integer) this.getAccountTo().getId()).toString(),
                this.getValue().toString(), this.getDate().toString());
    }
}
