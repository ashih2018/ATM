package ATM_0354;

import java.math.BigDecimal;

public class BankDaddy extends BankEmployee {

    public BankDaddy(String username, String password) {
        super(username, password);
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
            } else { // It is a Transfer
                User otherUser;
                if (((Transfer) transaction).getUsernameFrom().equals(user.getUsername())) {
                    otherUser = (User) Main.atm.userHandler.getUser(((Transfer) transaction).getUsernameTo());
                    Account otherAccount = otherUser.getAccount(transaction.getAccountIdTo());
                    transferBackMoney(userAccount, otherAccount, transaction);
                } else {
                    otherUser = (User) Main.atm.userHandler.getUser(((Transfer) transaction).getUsernameFrom());
                    Account otherAccount = otherUser.getAccount(transaction.getAccountIdFrom());
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
