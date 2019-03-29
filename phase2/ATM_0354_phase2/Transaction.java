package ATM_0354_phase2;

import com.sun.istack.internal.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Transaction {

    private LocalDateTime date;
    private Account accountFrom, accountTo;
    private BigDecimal value;

    /**
     * accountFrom can be null iff instanceof Deposit
     * accountTo can be null iff instanceof Bill or Withdrawal
     */
    public Transaction(@Nullable Account accountFrom, @Nullable Account accountTo, BigDecimal value, LocalDateTime date) {
        this.date = date;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.value = value;
    }


    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime newDate){
        this.date = newDate;
    }
    public Account getAccountFrom() {
        return accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void process() {
        if (!getAccountFrom().canTransferOut()) {
            System.out.println("Account unable to withdraw from.");
            return;
        }
        try {
            getAccountFrom().transferMoneyOut(getValue());
            getAccountFrom().addTransaction(this);
        } catch (MoneyTransferException e) {
            e.printStackTrace();
        }
    }

    public abstract String serialize();
    public abstract String view();


    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " : Account ID Number " + this.accountFrom + " sent $" + value +
                " to " + this.accountTo;
    }

    public abstract void undo();
}
