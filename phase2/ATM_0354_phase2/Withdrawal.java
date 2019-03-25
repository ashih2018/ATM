package ATM_0354_phase2;

import java.math.BigDecimal;

public class Withdrawal extends Transaction {
    public Withdrawal(Account accountFrom, BigDecimal value) {
        super(accountFrom, null, value);
    }

    @Override
    public void process(){
        if(!getAccountFrom().canTransferOut()){
            System.out.println("Account unable to transfer money out.");
            return;
        }

        try{
            getAccountFrom().transferMoneyOut(getValue());
            getAccountFrom().addTransaction(this);
        }
        catch(MoneyTransferException e){
            System.out.println(e.toString());
        }
    }

    @Override
    public String serialize() {
        return this.getClass().getSimpleName()
                + "," + getAccountFrom().getUsername() + "," + getAccountFrom().getId()
                + "," + getValue()
                + "," + getDate();
    }


}
