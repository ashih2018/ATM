package ATM_0354;

import java.math.BigDecimal;

public class BankDaddy extends BankEmployee {

    public static ATM atm;

    public BankDaddy(String username, String password) {
        super(username, password);
    }

    //TODO: decide exactly where new users should be created
    //This was implemented in UserHandler.
//    public User createUser(String username, String password) {
//        return new User(username, password);
//    }
    
    public void restock(BigDecimal billValue, int count) {
        atm.cashHandler.addCash(billValue, count);
    }

    public void undoRecentTransaction(User user, int accountId) throws MoneyTransferException {
        Transaction transaction = user.getAccount(accountId).getLastTransaction();
        if (!transaction.getIsBill()) {
            Account userAccount = user.getAccount(accountId);

            if (transaction instanceof Deposit || transaction instanceof Cheque) {
                userAccount.transferMoneyOut(transaction.getValue());
                userAccount.deleteSpecificTransaction(transaction);
            } else if (transaction instanceof Withdrawal) {
                userAccount.transferMoneyIn(transaction.getValue());
                userAccount.deleteSpecificTransaction(transaction);
            } else {
                User otherUser = atm.userHandler.getUserFromTransaction(transaction);
                if (otherUser != null) {
                    Account otherAccount = otherUser.getAccount(accountId);
                    if (transaction.getAccountIdFrom() == accountId) {
                        transferBackMoney(userAccount, otherAccount, transaction);
                    } else if (transaction.getAccountIdFrom() == otherAccount.getId()) {
                        transferBackMoney(otherAccount, userAccount, transaction);
                    }
                } else {
                    throw new MoneyTransferException("The user that did not request the transaction undo does not exist");
                }
            }
        } else {
            System.out.println("Can't undo a bill transaction");
        }
    }
    public void transferBackMoney (Account transferIn, Account transferOut, Transaction transaction) {
        if (transferIn instanceof AssetAccount) {
            transferIn.forceTransferIn(transaction.getValue());
        } else if (transferIn instanceof DebtAccount) {
            ((DebtAccount) transferIn).decreaseDebt(transaction.getValue());
        }
        if (transferOut instanceof AssetAccount) {
            transferOut.forceTransferOut(transaction.getValue());
        } else if (transferOut instanceof DebtAccount) {
            ((DebtAccount) transferOut).increaseDebt(transaction.getValue());
        }
        transferIn.deleteSpecificTransaction(transaction);
        transferOut.deleteSpecificTransaction(transaction);
    }




}
