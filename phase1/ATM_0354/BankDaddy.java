package ATM_0354;

import java.math.BigDecimal;

public class BankDaddy extends BankEmployee {

    public static ATM atm;

    public BankDaddy(String username, String password) {
        super(username, password);
    }

    //TODO: decide exactly where new users should be created
    public User createUser(String username, String password) {
        return new User(username, password);
    }
    
    public void restock(BigDecimal billValue, int count) {
        atm.cashHandler.addCash(billValue, count);
    }

    public void undoRecentTransaction(User user, int accountId) throws MoneyTransferException {
        Transaction transaction = user.getAccount(accountId).getLastTransaction();
        if (!transaction.getIsBill()) {
            Account userAccount = user.getAccount(accountId);
            User otherUser = atm.userHandler.getUserFromAccountId(accountId);
            if (otherUser != null) {
                Account otherAccount = otherUser.getAccount(accountId);
                if (transaction.getAccountIdFrom() == accountId) {
                    if (userAccount instanceof AssetAccount) {
                        userAccount.forceTransferIn(transaction.getValue());
                    } else if (userAccount instanceof DebtAccount) {
                        ((DebtAccount) userAccount).decreaseDebt(transaction.getValue());
                    }
                    if (otherAccount instanceof AssetAccount) {
                        otherAccount.forceTransferOut(transaction.getValue());
                    } else if (otherAccount instanceof DebtAccount) {
                        ((DebtAccount) otherAccount).increaseDebt(transaction.getValue());
                    }
                    userAccount.deleteSpecificTransaction(transaction);
                    otherAccount.deleteSpecificTransaction(transaction);
                } else if (transaction.getAccountIdFrom() == otherAccount.getId()) {
                    if (otherAccount instanceof AssetAccount) {
                        otherAccount.forceTransferIn(transaction.getValue());
                    } else if (otherAccount instanceof DebtAccount) {
                        ((DebtAccount) otherAccount).decreaseDebt(transaction.getValue());
                    }
                    if (userAccount instanceof AssetAccount) {
                        userAccount.forceTransferOut(transaction.getValue());
                    } else if (userAccount instanceof DebtAccount) {
                        ((DebtAccount) userAccount).increaseDebt(transaction.getValue());
                    }
                    userAccount.deleteSpecificTransaction(transaction);
                    otherAccount.deleteSpecificTransaction(transaction);
                }
            } else {
                throw new MoneyTransferException("The user that did not request the transaction undo does not exist");
            }
        }
    }



}
