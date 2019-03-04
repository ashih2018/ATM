package ATM_0354;

import java.time.LocalDate;
import java.util.Date;
import java.time.LocalDateTime;

public class ATM {
    private LocalDateTime datetime;
    UserHandler userHandler;
    CashHandler cashHandler;
    InputHandler inputHandler;

    /**
     * Initialize an empty ATM whose time is set to the current time;
     */
    public ATM() {
        this.datetime = LocalDateTime.now();
        this.userHandler = new UserHandler();
        this.cashHandler = new CashHandler();
        this.inputHandler = new InputHandler();
    }

    /**
     * Set the ATM's date and time.
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     */
    void setDateTime(int year, int month, int day, int hour, int minute, int second) {
        this.datetime = LocalDateTime.of(year, month, day, hour, minute, second);
    }

    /**
     * Set the ATM's date and time.
     *
     * @param datetime
     */
    void setDateTime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    /**
     * Try to sign in a user
     *
     * @param username The username of the user that is trying to sign in.
     * @param password The inputted password.
     * @return True if the username and password check out, false otherwise.
     */
    public boolean signIn(String username, String password) {
        return this.userHandler.verifyUser(username, password);
    }

    /**
     * Withdraw money into a user's account.
     *
     * @param user  The user to withdraw money from
     * @param money The amount of money to withdraw.
     * @param id    The id of the account to withdraw from.
     * @return True if the withdrawal succeeds, false otherwise.
     */
    public boolean withdraw(User user, double money, int id) {
        //TODO: complete
        return true;
    }

    /**
     * Deposit money into a user's account.
     *
     * @param user  The user that is depositing money.
     * @param money The amount of money the User is depositing.
     * @param id    The id of the account to deposit into.
     * @return True if the deposit succeeds, false otherwise.
     */
    public boolean deposit(User user, double money, int id) {
        //TODO: complete
        return true;
    }

    /**
     * Get a summary of a user's account
     *
     * @param user The user whose account we want a summary of.
     * @param id   The id of the account to be summarized.
     * @return A String representation of the user's account with id {id}.
     */
    public String viewAccount(User user, int id) {
        //TODO: complete
        return "";
    }

    /**
     * Transfer money from one account to another.
     *
     * @return True if the transaction succeeds, false otherwise.
     */
    public boolean transfer() {
        return true;
    }

    /**
     * Pay a bill
     *
     * @return True if the payment succeeds, false otherwise.
     */
    public boolean payBill() {
        //TODO: figure out proper type signature + name conflict with Bill
        return true;
    }

    /**
     * Create a new User Account
     *
     * @param TODO: figure this out
     * @return True if the creation succeeds, false otherwise.
     */
    public boolean createAccount(User user, String accountType) { //TODO: Figure out parameters
        return true;

    }
}
