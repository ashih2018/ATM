package ATM_0354_phase2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Withdrawal extends Transaction {
    public Withdrawal(Account accountFrom, int value) {
        super(accountFrom, null, new BigDecimal(value), Main.atm.getDateTime());
    }

    Withdrawal(Account accountFrom, int value, LocalDateTime date) {
        super(accountFrom, null, new BigDecimal(value), date);
    }

    public void process() {
        if(Main.atm.cashHandler.withdrawCash(this.getValue().intValue())) super.process();
        else System.out.println("Can't withdraw this amount, there's not enough cash.");
    }

    @Override
    public String serialize() {
        return String.join(",",
                this.getClass().getSimpleName(), ((Integer) this.getAccountFrom().getId()).toString(),
                this.getValue().toString(), this.getDate().toString());
    }

    @Override
    public String view() {
        return String.format("Withdrawal of %f from %s's account %d", this.getValue().doubleValue(), this.getAccountFrom().getUsername(), this.getAccountFrom().getId());
    }

    @Override
    public void undo() {
        this.getAccountFrom().forceTransferIn(this.getValue());
        this.getAccountFrom().undoTransaction(this);
        int[] cashValues = {50, 20, 10, 5};
        int money = this.getValue().intValue();
        for(int value: cashValues){
            int numBills = money / value;
            money -= numBills*value;
            Main.atm.cashHandler.addCash(value, numBills);
        }

    }

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = getDate().format(formatter);
        return formatDateTime + "\n" +
                "\tWithdrawal: $" + getValue() + " from user \'" + getAccountFrom().getUsername() + "\' account #" + getAccountFrom().getId();}
}
