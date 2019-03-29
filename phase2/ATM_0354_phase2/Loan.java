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

public class Loan extends Transaction {

    private BigDecimal price;
    private BigDecimal interest;

    public Loan(@Nullable Account accountTo, BigDecimal value, BigDecimal interest) {
        super(null, accountTo, value, LocalDateTime.now());
        this.price = value;
        this.interest = interest;
//        this.compound();
    }

    public Loan(@Nullable Account accountTo, BigDecimal value, BigDecimal interest, LocalDateTime date) {
        super(null, accountTo, value, date);
        this.price = value;
        this.interest = interest;
    }

    private void compound(int days) {
        price = price.multiply(interest.pow(days));

    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    private void writeLoan() {
        try (FileWriter fw = new FileWriter("phase2/ATM_0354_phase2/Files/outgoing.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println(this.toString());
        } catch (IOException e) {
            System.out.println("IOException writing to outgoing.txt");
        }
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
        return this.getClass().getSimpleName()
                + "," + getAccountTo().getUsername() + "," + getAccountTo().getId()
                + "," + getValue()
                + "," + getDate();
    }

    @Override
    public String toString() {
        return "The bank loaned $" + getValue() + " to account ID Number " + getAccountTo();
    }

}
