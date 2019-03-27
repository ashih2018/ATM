package ATM_0354_phase2;

public abstract class BankEmployee extends User {
    public BankEmployee(String username, String password, String salt) {
        super(username, password, salt);
    }
}
