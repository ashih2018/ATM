package ATM_0354_phase1;

import ATM_0354_phase2.Account;
import ATM_0354_phase2.Bill;
import ATM_0354_phase2.Cheque;
import ATM_0354_phase2.Deposit;
import ATM_0354_phase2.MoneyTransferException;
import ATM_0354_phase2.Transaction;
import ATM_0354_phase2.Transfer;
import ATM_0354_phase2.Withdrawal;

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

    @Override
    public void sendTransfer(ATM_0354_phase2.Transfer transfer) throws ATM_0354_phase2.MoneyTransferException {
        if (canTransferOut()) {
            increaseDebt(transfer.getValue());
            this.addTransaction(transfer);
        } else throw new ATM_0354_phase2.MoneyTransferException("Can't transfer money out of this account!");
    }

    @Override
    public void receiveTransfer(Transfer transfer) throws ATM_0354_phase2.MoneyTransferException {
        if (canTransferIn()) {
            decreaseDebt(transfer.getValue());
            this.addTransaction(transfer);
        }
    }

    @Override
    public void processDeposit(Deposit deposit) throws ATM_0354_phase2.MoneyTransferException {
        if (canTransferIn()) {
            decreaseDebt(deposit.getValue());
            this.addTransaction(deposit);
        }
    }

    @Override
    public void processWithdrawal(Withdrawal withdrawal) throws ATM_0354_phase2.MoneyTransferException {
        if (canTransferOut()) {
            increaseDebt(withdrawal.getValue());
            this.addTransaction(withdrawal);
        } else throw new ATM_0354_phase2.MoneyTransferException("Can't transfer money out of this account!");
    }

    @Override
    public void processCheque(Cheque cheque) throws ATM_0354_phase2.MoneyTransferException {
        if (canTransferIn()) {
            decreaseDebt(cheque.getValue());
            this.addTransaction(cheque);
        }
    }

    @Override
    public void processBill(Bill bill) throws ATM_0354_phase2.MoneyTransferException {
        if (canTransferOut()) {
            increaseDebt(bill.getValue());
            this.addTransaction(bill);
        } else throw new MoneyTransferException("Can't transfer money out of this account!");
    }

    @Override
    public String toString() {
        return "Account ID: " + this.getId() + "\nAmount Owed: " + this.getBalance();
    }
}
