package ATM_0354;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.time.LocalDateTime;

public class ATM {
    private LocalDateTime datetime;
    UserHandler userHandler;
    CashHandler cashHandler;
    Person curUser;

    /**
     * Initialize an empty ATM whose time is set to the current time;
     */
    public ATM() {
        this.datetime = LocalDateTime.now();
        this.userHandler = new UserHandler();
        this.cashHandler = new CashHandler();
        this.curUser = null;
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

    public void setCurUser(Person p) {
        this.curUser = p;
    }
    public Person getCurUser(){
        return curUser;
    }

    /**
     * Try to sign in a user
     *
     * @param username The username of the user that is trying to sign in.
     * @param password The inputted password.
     * @return True if the username and password check out, false otherwise.
     */
    public Person signIn(String username, String password) {
        this.curUser = this.userHandler.verifyUser(username, password);
        return curUser;
    }
    public void signOut(){
        this.curUser = null;
    }
    /**
     * Withdraw money into a user's account.
     *
     * @param user  The user to withdraw money from
     * @param money The amount of money to withdraw.
     * @param id    The id of the account to withdraw from.
     */
    public boolean withdraw(User user, BigDecimal money, int id) {
        return userHandler.withdraw(user, money, id);
    }



    /**
     * Deposit money into a user's account.
     *
     * @param user  The user that is depositing money.
     * @param money The amount of money the User is depositing.
     * @param id    The id of the account to deposit into.
     * @return True if the deposit succeeds, false otherwise.
     */
    public boolean deposit(User user, BigDecimal money, int id) {
        return userHandler.deposit(user, money, id);
    }

    /**
     * Get a summary of the current user's accounts
     * Precondition: this.curUser != null && this.curUser instanceof User
     * @return A String representation of the current user's accounts.
     */
    public String viewCurAccounts(){
        return this.viewAccount((User)this.curUser);
    }

    /**
     * Get a summary of a user's accounts
     *
     * @param user The user whose account we want a summary of.
     * @return A String representation of the user's accounts.
     */
    private String viewAccount(User user) {
        return user.getSummary();
    }
    /**
     * Get the total money of the current user.
     * @return the amount of money the current user has.
     */
    public BigDecimal getCurMoney(){
        return this.getMoney((User)this.curUser);
    }

    /**
     * Get the total money of a user.
     * @param user
     * @return the amount of money the user has.
     */
    private BigDecimal getMoney(User user){
        return user.getAccountTotal();
    }
    /**
     * Request a new account for the current user
     * Precondition: this.curUser != null && this.curUser instanceof User
     * @param accountType
     */
    public void requestCurAccount(String accountType){
        this.requestAccount((User)this.curUser, accountType);
    }

    /**
     * Request a new account for {user} of type {accountType}.
     * @param user The User who is requesting a new account.
     * @param accountType The type of account the user wishes to open.
     */
    public void requestAccount(User user, String accountType){
        this.userHandler.requestAccount(user, accountType);
    }

    //TODO: complete these functions
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
        //TODO: figure out proper type signature
        return true;
    }

    /**
     * Create a new Person
     * @param type The type of Person to create.
     * @param username The username of the new Person.
     * @param password The password of the new Person.
     */
    public void createPerson(String type, String username, String password){
        userHandler.createUser(type, username, password);
    }

    /**
     * Check if a username already exists.
     * @param username The username to check.
     * @return true if the username already exists, false otherwise.
     */
    public boolean usernameExists(String username){
        return userHandler.usernameExists(username);
    }
}
