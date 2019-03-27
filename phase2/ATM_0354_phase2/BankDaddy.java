package ATM_0354_phase2;

public class BankDaddy extends BankEmployee {

    public BankDaddy(String username, String password, String salt) {
        super(username, password, salt);
    }

    public void undoRecentTransaction(User user, int accountId) throws MoneyTransferException {
        Transaction transaction = user.getAccount(accountId).getLastTransaction();
        if (!(transaction instanceof Bill)) {
            Account userAccount = user.getAccount(accountId);

            if (transaction instanceof Deposit) {
                userAccount.transferMoneyOut(transaction.getValue());
                userAccount.deleteSpecificTransaction(transaction);
            } else if (transaction instanceof Withdrawal) {
                userAccount.transferMoneyIn(transaction.getValue());
                userAccount.deleteSpecificTransaction(transaction);
            } else { // It is a Transfer
                Account accountFrom = transaction.getAccountFrom();
                Account accountTo = transaction.getAccountTo();
                transferBackMoney(accountFrom, accountTo, transaction);
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
        } else if (transferOut instanceof LineOfCreditAccount) {
            ((LineOfCreditAccount) transferOut).increaseDebt(transaction.getValue());
        }
        transferIn.deleteSpecificTransaction(transaction);
        transferOut.deleteSpecificTransaction(transaction);
    }




}
