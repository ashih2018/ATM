package ATM_0354_phase1;

import ATM_0354_phase2.Account;
import ATM_0354_phase2.AssetAccount;
import ATM_0354_phase2.BankEmployee;
import ATM_0354_phase2.DebtAccount;
import ATM_0354_phase2.Deposit;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.MoneyTransferException;
import ATM_0354_phase2.Transaction;
import ATM_0354_phase2.Transfer;
import ATM_0354_phase2.User;
import ATM_0354_phase2.Withdrawal;

public class BankDaddy extends BankEmployee {

    public BankDaddy(String username, String password) {
        super(username, password);
    }

    public void undoRecentTransaction(ATM_0354_phase2.User user, int accountId) throws MoneyTransferException {
        ATM_0354_phase2.Transaction transaction = user.getAccount(accountId).getLastTransaction();
        if (!transaction.getIsBill()) {
            ATM_0354_phase2.Account userAccount = user.getAccount(accountId);

            if (transaction instanceof Deposit || transaction instanceof Cheque) {
                userAccount.transferMoneyOut(transaction.getValue());
                userAccount.deleteSpecificTransaction(transaction);
            } else if (transaction instanceof Withdrawal) {
                userAccount.transferMoneyIn(transaction.getValue());
                userAccount.deleteSpecificTransaction(transaction);
            } else { // It is a Transfer
                ATM_0354_phase2.User otherUser;
                if (((ATM_0354_phase2.Transfer) transaction).getUsernameFrom().equals(user.getUsername())) {
                    otherUser = (ATM_0354_phase2.User) ATM_0354_phase2.Main.atm.userHandler.getUser(((ATM_0354_phase2.Transfer) transaction).getUsernameTo());
                    ATM_0354_phase2.Account otherAccount = otherUser.getAccount(transaction.getAccountTo());
                    transferBackMoney(userAccount, otherAccount, transaction);
                } else {
                    otherUser = (User) Main.atm.userHandler.getUser(((Transfer) transaction).getUsernameFrom());
                    ATM_0354_phase2.Account otherAccount = otherUser.getAccount(transaction.getAccountFrom());
                    transferBackMoney(otherAccount, userAccount, transaction);
                }

                if (otherUser == null) { // Should never happen
                    System.out.println("The other user is null for some reason!!!");
                }
            }
        } else {
            System.out.println("Can't undo a bill transaction");
        }
    }
    public void transferBackMoney (ATM_0354_phase2.Account transferIn, Account transferOut, Transaction transaction) {
        if (transferIn instanceof ATM_0354_phase2.AssetAccount) {
            transferIn.forceTransferIn(transaction.getValue());
        } else if (transferIn instanceof ATM_0354_phase2.DebtAccount) {
            ((ATM_0354_phase2.DebtAccount) transferIn).decreaseDebt(transaction.getValue());
        }
        if (transferOut instanceof AssetAccount) {
            transferOut.forceTransferOut(transaction.getValue());
        } else if (transferOut instanceof ATM_0354_phase2.DebtAccount) {
            ((DebtAccount) transferOut).increaseDebt(transaction.getValue());
        }
        transferIn.deleteSpecificTransaction(transaction);
        transferOut.deleteSpecificTransaction(transaction);
    }




}
