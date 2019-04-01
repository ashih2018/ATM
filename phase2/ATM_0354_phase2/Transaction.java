package ATM_0354_phase2;

import com.sun.istack.internal.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public void setValue(BigDecimal value){
        this.value = value;
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
            System.out.println("You can't withdraw that amount from this account. Try again.");
        }
    }

    public abstract String serialize();
    public abstract String view();


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = getDate().format(formatter);
        return formatDateTime + "\n" +
                "\tTransaction: $" + getValue() + " from user \'" + getAccountFrom().getUsername()
                + "\' to user \'" + getAccountTo().getUsername() + "\'";
    }

    public abstract void undo();
}
