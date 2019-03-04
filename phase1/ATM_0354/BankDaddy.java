package ATM_0354;

public class BankDaddy extends BankEmployee {

    public BankDaddy() {

    }

    public User createUser(String username, String password) {
        return new User(username, password);
    }

    public void restock() {

    }

    public Transaction undoRecentTransaction(User user, int accountNum) {
        return null;
    }



}
