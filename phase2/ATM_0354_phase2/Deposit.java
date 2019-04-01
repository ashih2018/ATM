package ATM_0354_phase2;

import com.sun.istack.internal.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deposit extends Transaction {
    public Deposit (@NotNull Account accountTo, BigDecimal value) {
        super(null, accountTo, value, Main.atm.getDateTime());
    }

    public Deposit (@NotNull Account accountTo, BigDecimal value, LocalDateTime date) {
        super(null, accountTo, value, date);
    }

    @Override
    public void process(){
        if(!getAccountTo().canTransferIn()){
            System.out.println("Account unable to transfer money in.");
            return;
        }
        try{
            getAccountTo().transferMoneyIn(getValue());
            getAccountTo().addTransaction(this);
        }
        catch(MoneyTransferException e){
            System.out.println(e.toString());
        }
    }
    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = getDate().format(formatter);
        return formatDateTime + "\n" +
                "\tDeposit: $" + getValue()
                + "\' to user \'" + getAccountTo().getUsername() + "\' account #" +getAccountTo().getId();
    }

    @Override
    public void undo() {
        getAccountTo().forceTransferOut(getValue());
        getAccountTo().undoTransaction(this);
    }

    @Override
    public String serialize() {
        return String.join(",",
                this.getClass().getSimpleName(), ((Integer) this.getAccountTo().getId()).toString(),
                this.getValue().toString(), this.getDate().toString());
    }

    @Override
    public String view() {
        return String.format("Deposit of %f into %s's account %d", this.getValue().doubleValue(), this.getAccountTo().getUsername(), this.getAccountTo().getId());
    }
}
