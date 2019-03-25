package ATM_0354_phase2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class Bill extends Transaction {
    public Bill (Account accountFrom, BigDecimal value) {
        super(accountFrom, null, value);
    }

    @Override
    public void process(){
        super.process();
        writeBill();
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
    public String toString() {
        return "Account ID Number " + getAccountFrom() + " paid a $" + getValue() + " bill.";
    }
}
