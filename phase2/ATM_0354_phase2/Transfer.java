package ATM_0354_phase2;

import com.sun.istack.internal.NotNull;

import java.math.BigDecimal;

public class Transfer extends Transaction {

    public Transfer(@NotNull Account accountFrom,@NotNull Account accountTo, BigDecimal value) {
        super(accountFrom, accountTo, value);
    }

    @Override
    public void process(){
        if(!getAccountFrom().canTransferOut() || !getAccountTo().canTransferIn()){
            System.out.println("Accounts unable to transfer.");
            return;
        }

        try{
            getAccountFrom().transferMoneyOut(getValue());
            getAccountTo().transferMoneyIn(getValue());
            getAccountFrom().addTransaction(this);
            getAccountTo().addTransaction(this);
        }
        catch(MoneyTransferException e){
            System.out.println(e.toString());
        }
    }

    @Override
    public String toString() {
        return super.getAccountFrom() + " sent $" + getValue() + " to " + super.getAccountTo();
    }
}