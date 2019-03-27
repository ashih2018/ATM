package ATM_0354_phase2;

import java.math.BigDecimal;
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

    void setDateTime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public LocalDateTime getDateTime(){
        return this.datetime;
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
    public boolean withdraw(User user, int money, int id) {
        try {
            boolean success = userHandler.withdraw(user, money, id);
            if (success) {
                withdrawCashFromATM(money);
            }
            return success;
        } catch (MoneyTransferException e) {
            System.out.println("Could not withdraw money!");
        } return false;
    }

    private void withdrawCashFromATM(int money) {
        if(money % 5 != 0)
            System.out.println("invalid amount to withdraw");
        int[] cashValues = {50, 20, 10, 5};
        for(int cashValue: cashValues){
            int numBills = money / cashValue;
            money -= cashValue*numBills;
            cashHandler.withdrawCash(cashValue, numBills);
        }
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
        try {
            return userHandler.deposit(user, money, id);
        } catch (MoneyTransferException e) {
            System.out.println("Could not deposit money!");
        } return false;
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

    private BigDecimal getMoney(User user){
        return user.getAccountTotal();
    }
    /**
     * Request a new account for the current user
     * Precondition: this.curUser != null && this.curUser instanceof User
     * @param accountType The type of account to request.
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
        user.requestAccount(accountType);
    }

    public void requestJointAccount(User curUser, User otherUser, String accountType){
        curUser.requestJointAccount(otherUser, accountType);
    }

    /**
     * Create a new Person
     * @param type The type of Person to create.
     * @param username The username of the new Person.
     * @param password The password of the new Person.
     */
    public void createPerson(String type, String username, String password, String salt){
        userHandler.createUser(type, username, password, salt);
    }

    /**
     * Check if a username already exists.
     * @param username The username to check.
     * @return true if the username already exists, false otherwise.
     */
    public boolean usernameExists(String username){
        return userHandler.usernameExists(username);
    }

    public Person getUser(String username) {
        return userHandler.getUser(username);
    }

    public void addCash(int billValue, int count){
        cashHandler.addCash(billValue, count);
    }
}
