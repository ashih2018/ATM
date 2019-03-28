package ATM_0354_phase2;

import com.sun.istack.internal.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transfer extends Transaction {

    public Transfer(@NotNull Account accountFrom, @NotNull Account accountTo, BigDecimal value) {
        super(accountFrom, accountTo, value, LocalDateTime.now());
    }

    public Transfer(@NotNull Account accountFrom, @NotNull Account accountTo, BigDecimal value, LocalDateTime date) {
        super(accountFrom, accountTo, value, date);
    }

    @Override
    public void process() {
        if (!getAccountFrom().canTransferOut() || !getAccountTo().canTransferIn()) {
            System.out.println("Accounts unable to transfer.");
            return;
        }
        try {
            getAccountFrom().transferMoneyOut(getValue());
            getAccountTo().transferMoneyIn(getValue());
            getAccountFrom().addTransaction(this);
            getAccountTo().addTransaction(this);
        } catch (MoneyTransferException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String serialize() {
        return String.join(",", this.getAccountFrom().getUsername(),
                this.getClass().getSimpleName(), ((Integer) this.getAccountFrom().getId()).toString(),
                getAccountTo().getUsername(), ((Integer) getAccountTo().getId()).toString(), this.getValue().toString(),
                this.getDate().toString());
    }

    @Override
    public String toString() {
        return super.getAccountFrom() + " sent $" + getValue() + " to " + super.getAccountTo();
    }
}