package ATM_0354_phase2;

import java.math.BigDecimal;
import java.net.PasswordAuthentication;
import java.time.Duration;
import java.time.LocalDateTime;

public class ATM {
    private LocalDateTime datetime;
    UserHandler userHandler;
    CashHandler cashHandler;
    ChequeHandler chequeHandler;
    EmailHandler emailHandler;
    StockHandler stockHandler;
    private Person curUser;

    /**
     * Initialize an empty ATM whose time is set to the current time;
     */
    public ATM() {
        this.datetime = LocalDateTime.now();
        this.userHandler = new UserHandler();
        this.cashHandler = new CashHandler();
        this.chequeHandler = new ChequeHandler();
        this.emailHandler = new EmailHandler();
        this.stockHandler = new StockHandler();
        this.curUser = null;
    }

    public int numUsers(){
        return this.userHandler.users.size();
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

    public int newDate(LocalDateTime newDate){
        if(this.datetime.compareTo(newDate) > 0){
            System.out.println("Invalid date");
            return 0;
        }
        int deltaMonths = 0;
        if(newDate.getYear() > this.datetime.getYear() || newDate.getMonthValue()>this.datetime.getMonthValue()){ //new month
            deltaMonths = (newDate.getYear()-this.datetime.getYear())*12
                            + newDate.getMonthValue()-this.datetime.getMonthValue();
        }
        this.datetime = newDate;
        return deltaMonths;
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
        return this.viewAccounts((User)this.curUser);
    }

    /**
     * Get a summary of a user's accounts
     *
     * @param user The user whose account we want a summary of.
     * @return A String representation of the user's accounts.
     */
    private String viewAccounts(User user) {
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

    /**
     * Send an email summary to the specified user and the email
     * @param user The user whose account summary to get
     * @param emailTo The email to send the summary to
     */
    public void sendEmailSummary(User user, String emailTo) {
        EmailHandler.sendEmailSummary(user, emailTo);
    }

    /**
     * Process all of the electronic cheques
     */
    public void processEmailCheques() {
        this.chequeHandler.processCheques();
    }

    public Person getUser(String username) {
        return userHandler.getUser(username);
    }

    public void addCash(int billValue, int count){
        cashHandler.addCash(billValue, count);
    }

    public String getCashSummary(){
        return this.cashHandler.getSummary();
    }
}
