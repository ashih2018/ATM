package ATM_0354_phase2;

import com.sun.istack.internal.NotNull;

import java.math.BigDecimal;

public class Deposit extends Transaction {
    public Deposit (@NotNull Account accountTo, BigDecimal value) {
        super(null, accountTo, value);
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
}
