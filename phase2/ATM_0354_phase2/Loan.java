package ATM_0354_phase2;

import com.sun.istack.internal.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Loan extends Transaction implements Comparable<Loan>{

    private BigDecimal interest;
    private LocalDateTime endDate;
    private boolean paidOff;
    private BigDecimal original;


    public Loan(@Nullable Account accountTo, BigDecimal value, BigDecimal interest, LocalDateTime endDate) {
        super(null, accountTo, value, LocalDateTime.now());
        this.original = value;
        this.interest = interest;
        this.endDate = endDate;
        this.paidOff = false;
    }

    public Loan(@Nullable Account accountTo, BigDecimal value, BigDecimal interest, LocalDateTime date, LocalDateTime endDate) {
        super(null, accountTo, value, date);
        this.original = value;
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
            Loan newLoan = new Loan(this.getAccountTo(), this.getValue(),this.interest, this.endDate.plusMonths(4));
            this.getAccountTo().addTransaction(newLoan);
            newLoan.process();
        } else if(monthsLeft < 3 && Main.atm.getUser(this.getAccountTo().getUsername()).hasEmail()){
            EmailHandler.notifyLoan(Main.atm.getUser(this.getAccountTo().getUsername()).email, this);
        }
    }
    String endMonth(){
        return this.endDate.getMonth()+" "+this.endDate.getYear();
    }
    public boolean isPaid(){
        return this.paidOff;
    }
    public boolean pay(BigDecimal amount){
        if(amount.compareTo(this.getValue()) > 0) return false;
        this.setValue(this.getValue().subtract(amount));
        if(this.getValue().compareTo(BigDecimal.ZERO) == 0) this.paidOff = true;
        return true;
    }
    private void compound(int months) {
        this.setValue(this.getValue().multiply(interest.pow(months)));
    }

    public void recordLoan() {
        getAccountTo().addTransaction(this);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = getDate().format(formatter);
        return formatDateTime + "\n" +
                "\tLoan: $" + getValue() + " to account ID #" + getAccountTo().getId();
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
