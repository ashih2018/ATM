package ATM_0354_phase1;

import ATM_0354_phase2.CashHandler;
import ATM_0354_phase2.MoneyTransferException;
import ATM_0354_phase2.Person;
import ATM_0354_phase2.User;
import ATM_0354_phase2.UserHandler;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ATM {
    private LocalDateTime datetime;
    ATM_0354_phase2.UserHandler userHandler;
    ATM_0354_phase2.CashHandler cashHandler;
    ATM_0354_phase2.Person curUser;

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
     * @param datetime
     */
    void setDateTime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public LocalDateTime getDateTime(){
        return this.datetime;
    }

    public ATM_0354_phase2.Person getCurUser(){
        return curUser;
    }

    /**
     * Try to sign in a user
     *
     * @param username The username of the user that is trying to sign in.
     * @param password The inputted password.
     * @return True if the username and password check out, false otherwise.
     */
    public ATM_0354_phase2.Person signIn(String username, String password) {
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
    public boolean withdraw(ATM_0354_phase2.User user, BigDecimal money, int id) {
        try {
            boolean success = userHandler.withdraw(user, money, id);
            if (success) {
                withdrawCashFromATM(money);
            }
            return success;
        } catch (ATM_0354_phase2.MoneyTransferException e) {
            System.out.println("Could not withdraw money!");
        } return false;
    }

    private void withdrawCashFromATM(BigDecimal money) {
        double cash = money.doubleValue();
        int fifties = 0;
        int twenties = 0;
        int tens = 0;
        int fives = 0;
        while (cash >= 5) {
            if (cash >= 50) {
                fifties += 1;
                cash -= 50;
            }
            else if (cash >= 20) {
                twenties += 1;
                cash -= 20;
            }
            else if (cash >= 10) {
                tens += 1;
                cash -= 10;
            }
            else if (cash >= 5) {
                fives += 1;
                cash -= 5;
            }
        }
        cashHandler.withdrawCash(new BigDecimal(50), fifties);
        cashHandler.withdrawCash(new BigDecimal(20), twenties);
        cashHandler.withdrawCash(new BigDecimal(10), tens);
        cashHandler.withdrawCash(new BigDecimal(5), fives);
    }



    /**
     * Deposit money into a user's account.
     *
     * @param user  The user that is depositing money.
     * @param money The amount of money the User is depositing.
     * @param id    The id of the account to deposit into.
     * @return True if the deposit succeeds, false otherwise.
     */
    public boolean deposit(ATM_0354_phase2.User user, BigDecimal money, int id) {
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
        return this.viewAccount((ATM_0354_phase2.User)this.curUser);
    }

    /**
     * Get a summary of a user's accounts
     *
     * @param user The user whose account we want a summary of.
     * @return A String representation of the user's accounts.
     */
    private String viewAccount(ATM_0354_phase2.User user) {
        return user.getSummary();
    }
    /**
     * Get the total money of the current user.
     * @return the amount of money the current user has.
     */
    public BigDecimal getCurMoney(){
        return this.getMoney((ATM_0354_phase2.User)this.curUser);
    }

    /**
     * Get the total money of a user.
     * @param user
     * @return the amount of money the user has.
     */
    private BigDecimal getMoney(ATM_0354_phase2.User user){
        return user.getAccountTotal();
    }
    /**
     * Request a new account for the current user
     * Precondition: this.curUser != null && this.curUser instanceof User
     * @param accountType
     */
    public void requestCurAccount(String accountType){
        this.requestAccount((ATM_0354_phase2.User)this.curUser, accountType);
    }

    /**
     * Request a new account for {user} of type {accountType}.
     * @param user The User who is requesting a new account.
     * @param accountType The type of account the user wishes to open.
     */
    public void requestAccount(User user, String accountType){
        user.requestAccount(accountType);
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

    public Person getUser(String username) {
        return userHandler.getUser(username);
    }

    public void addCash(BigDecimal billValue, int count){
        cashHandler.addCash(billValue, count);
    }
}
