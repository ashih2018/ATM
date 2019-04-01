package ATM_0354_phase2;

import com.sun.istack.internal.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transfer extends Transaction {

    public Transfer(@NotNull Account accountFrom, @NotNull Account accountTo, BigDecimal value) {
        super(accountFrom, accountTo, value, Main.atm.getDateTime());
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
        return String.join(",", this.getClass().getSimpleName(), this.getAccountFrom().getUsername(),
                ((Integer) this.getAccountFrom().getId()).toString(),
                getAccountTo().getUsername(), ((Integer) getAccountTo().getId()).toString(), this.getValue().toString(),
                this.getDate().toString());
    }

    @Override
    public String view() {
        return String.format("Transfer of %f from %s's account %d to %s's account %d.", getValue().doubleValue(), getAccountFrom().getUsername(), getAccountFrom().getId(), getAccountTo().getUsername(), getAccountTo().getId());
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = getDate().format(formatter);
        return formatDateTime + "\n" +
                "\tTransfer: $" + getValue() + " from user \'" + getAccountFrom().getUsername() + "\' account #" + getAccountFrom().getId()
                + " to user \'" + getAccountTo().getUsername() + "\' account # " + getAccountTo().getId();
    }

    @Override
    public void undo() {
        getAccountFrom().forceTransferIn(getValue());
        getAccountTo().forceTransferOut(getValue());
        getAccountFrom().undoTransaction(this);
        getAccountTo().undoTransaction(this);
    }
}