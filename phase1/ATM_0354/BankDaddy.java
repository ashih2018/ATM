package ATM_0354;

public class BankDaddy extends BankEmployee {

    public BankDaddy(String username, String password) {
        super(username, password);
    }

    //TODO: decide exactly where new users should be created
    public User createUser(String username, String password) {
        return new User(username, password);
    }

    public void restock() {

    }

    public boolean undoRecentTransaction(User user, int accountNum) {
        Transaction t = user.getAccount(accountNum).getLastTransaction();
        if (t.getIsBill()) {
            return false;
        }
        else {
            user.getAccount(accountNum).undoTransaction();
            if (t.getUserFrom().equals(user.getUsername())) {
                user.getAccount(accountNum).transferMoneyIn(t.getValue());
                return true;
            }
            else {
                user.getAccount(accountNum).transferMoneyOut(t.getValue());
                return true;
            }
        }


    }



}
