package ATM_0354_phase2;

import com.sun.istack.internal.Nullable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class Loan extends Transaction implements Comparable<Loan>{

    private BigDecimal price;
    private BigDecimal interest;
    private LocalDateTime endDate;
    private boolean paidOff;
    private BigDecimal original;
    public Loan(@Nullable Account accountTo, BigDecimal value, BigDecimal interest, LocalDateTime endDate) {
        super(null, accountTo, value, LocalDateTime.now());
        this.original = value;
        this.price = value;
        this.interest = interest;
        this.endDate = endDate;
        this.paidOff = false;
    }

    public Loan(@Nullable Account accountTo, BigDecimal value, BigDecimal interest, LocalDateTime date, LocalDateTime endDate) {
        super(null, accountTo, value, date);
        this.original = value;
        this.price = value;
        this.interest = interest;
        this.endDate = endDate;
        this.paidOff = false;
    }

    public void changeMonth(int deltaMonths){
        if(isPaid()) return;
        this.compound(deltaMonths);
        this.setDate(this.getDate().plusMonths(deltaMonths));
        int monthsLeft = (endDate.getYear()-this.getDate().getYear())*12 + endDate.getMonthValue()-this.getDate().getMonthValue();
        if (monthsLeft <= 0) {
            //TODO: Behavior when a loan expires
        } else if(monthsLeft < 3){
            //TODO: Send an email out
        }
    }
    public boolean isPaid(){
        return this.paidOff;
    }
    public boolean pay(BigDecimal amount){
        if(amount.compareTo(price) > 0) return false;
        this.price = this.price.subtract(amount);
        if(price.compareTo(BigDecimal.ZERO) == 0) this.paidOff = true;
        return true;
    }
    private void compound(int months) {
        price = price.multiply(interest.pow(months));
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    @Override
    public void process() {
        if (!getAccountTo().canTransferIn()) {
            System.out.println("Accounts unable to transfer.");
            return;
        }
        try {
            getAccountTo().transferMoneyIn(getValue());
            getAccountTo().addTransaction(this);
        } catch (MoneyTransferException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String serialize() {
        return String.join(",",
                this.getClass().getSimpleName(), ((Integer) this.getAccountTo().getId()).toString(),
                this.getValue().toString(), this.interest.toString(), this.getDate().toString(), this.endDate.toString());
    }
    public String view(){
        return String.format("Loan of %f due before %s %d", getValue().doubleValue(), this.endDate.getMonth().toString(), this.endDate.getYear());
    }

    @Override
    public String toString() {
        return "The bank loaned $" + getValue() + " to account ID Number " + getAccountTo();
    }

    @Override
    public void undo() {
        this.getAccountTo().forceTransferOut(original);
        this.getAccountTo().undoTransaction(this);
    }

    @Override
    public int compareTo(Loan o) {
        return this.endDate.compareTo(o.endDate);
    }
}
