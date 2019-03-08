package ATM_0354;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class DebtAccount extends Account {
    // Debt accounts display a POSITIVE balance when user OWES money and vice versa

    public DebtAccount(int id) {
        super(id);
    }
    public DebtAccount(int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions){
        super(id, balance, dateOfCreation, transactions);
    }
    public void decreaseDebt(BigDecimal value) {
        this.setBalance(this.getBalance().subtract(value.setScale(2, BigDecimal.ROUND_HALF_UP)));
    }

    public void increaseDebt(BigDecimal value) {
        this.setBalance(this.getBalance().add(value.setScale(2, BigDecimal.ROUND_HALF_UP)));
    }

    // TODO: Change the way processTransaction() works !!!!
    @Override
    public void processTransaction(Transaction transaction) throws MoneyTransferException {
        if (transaction.getAccountIdFrom() == this.getId()) {
            if (canTransferOut()) {
                increaseDebt(transaction.getValue().setScale(2, BigDecimal.ROUND_HALF_UP));
                this.addTransaction(transaction);
            }
            else throw new MoneyTransferException("Can't transfer money out of this account!");
        } else  if (transaction.getAccountIdTo() == this.getId()) {
            if (canTransferIn()) {
                decreaseDebt(transaction.getValue().setScale(2, BigDecimal.ROUND_HALF_UP));
                this.addTransaction(transaction);
            }
        } else {
            System.out.println("Could not process transaction for account with ID: " + this.getId());
        }
    }

    @Override
    public String toString() {
        return "Account ID: " + this.getId() + "\nAmount Owed: " + this.getBalance();
    }
}
