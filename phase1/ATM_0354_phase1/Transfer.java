package ATM_0354_phase1;

import ATM_0354_phase2.Transaction;

import java.math.BigDecimal;

public class Transfer extends Transaction {

    private String usernameFrom, usernameTo;

    public Transfer(int accountIdFrom, int accountIdTo, String usernameFrom, String usernameTo, BigDecimal value) {
        super(accountIdFrom, accountIdTo, value, false);
        this.usernameFrom = usernameFrom;
        this.usernameTo = usernameTo;
    }

    public String getUsernameFrom() {
        return usernameFrom;
    }

    public String getUsernameTo() {
        return usernameTo;
    }

    @Override
    public String toString() {
        return usernameFrom + " sent $" + getValue() + " to " + usernameTo;
    }
}