package ATM_0354_phase2;

import com.sun.istack.internal.Nullable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

public class Loan extends Transaction {

    private BigDecimal price;
    private BigDecimal interest;

    public Loan (@Nullable Account accountTo, BigDecimal value, BigDecimal interest) {
        super(accountTo, null, value);
        this.price = value;
        this.interest = interest;
        this.compound();
    }

    //currently compounds every minute
    private void compound() {
        Timer timer = new Timer();
        timer.schedule( new TimerTask() {
            public void run() {
                price = price.multiply(interest);
            }
        }, 0, 60*1000);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    private void writeBill(){
        try(FileWriter fw = new FileWriter("phase2/ATM_0354_phase2/Files/outgoing.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw))
        {
            pw.println(this.toString());
        } catch (IOException e) {
            System.out.println("IOException writing to outgoing.txt");
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
