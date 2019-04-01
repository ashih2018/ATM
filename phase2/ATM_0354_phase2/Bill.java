package ATM_0354_phase2;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bill extends Transaction {
    String destination;

    public Bill(String destination, Account accountFrom, BigDecimal value, LocalDateTime date) {
        super(accountFrom, null, value, date);
        this.destination = destination;
    }
    public Bill(String destination, Account accountFrom, BigDecimal value) {
        super(accountFrom, null, value, LocalDateTime.now());
        this.destination = destination;
    }

    @Override
    public void process() {
        super.process();
        writeBill();
    }

    private void writeBill() {
        try {
            FileWriter fw = new FileWriter("phase2/ATM_0354_phase2/Files/outgoing.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(this.toString());
            pw.close();
            System.out.println("Bill paid (Printed to outgoing.txt)");
        } catch (IOException e) {
            System.out.println("IOException writing to outgoing.txt");
        }
    }

    @Override
    public String serialize() {
        return String.join(",", this.getClass().getSimpleName(), this.destination, ((Integer)this.getAccountFrom().getId()).toString(), this.getValue().toString(), this.getDate().toString());
    }

    @Override
    public String view() {
        return String.format("Bill of %f paid by %s's account %d", this.getValue().doubleValue(), this.getAccountFrom().getUsername(), this.getAccountFrom().getId());
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = getDate().format(formatter);
        return formatDateTime + "\n" +
                "\tBill: $" + getValue() + " from user \'" + getAccountFrom().getUsername() + "\' account #" + getAccountFrom().getId()
                + "\tDestination: " + destination;
    }

    @Override
    public void undo() {
        this.getAccountFrom().forceTransferIn(this.getValue());
        this.getAccountFrom().undoTransaction(this);
    }
}
