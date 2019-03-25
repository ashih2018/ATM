package ATM_0354_phase2;

import java.math.BigDecimal;
import java.util.ArrayList;

public class UserHandler {
    /**
     * An ArrayList that stores all users that are managed by this handler
     */
    ArrayList<Person> users;


    public UserHandler() {
        users = new ArrayList<>();
    }

    /**
     * Verify if a user's login information is correct
     * @param username The inputted username
     * @param password The inputted password
     * @return the person with this login information, or null if not found
     */
    public Person verifyUser(String username, String password) {
        for (Person user : users) {
            if (user.getUsername().equals(username)) {
                if (!user.getPassword().equals(password)) {
//                    System.err.println("Incorrect Password");
                    return null;
                } else
                    return user;
            }
        }
//        System.err.println("User not found");
        return null;
    }

    public static boolean verifyPassword(Person user, String password){
        return user.getHash().equals(PasswordHash.hashPassword(password, user.getSalt()));
    }

    /**
     * Create a new User.
     * Precondition: The username of the User is unique.
     * @param type The type of the new User
     * @param username The username of the new User.
     * @param password The password of the new User.
     */
    public void createUser(String type, String username, String password){
        if(type.equals("BankManager")){
            users.add(new BankDaddy(username, password));
        }
        else{
            users.add(new User(username, password));
        }
    }

    /**
     * Check whether or not a username already exists.
     * @param username The username to check
     * @return True if the username exists, false otherwise.
     */
    public boolean usernameExists(String username){
        for (Person person : users){
            if (person.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public Person getUser(String username) {
        for (Person person : users) {
            if (person.getUsername().equals(username)) {
                return person;
            }
        }
        return null;
    }

    /**
     * Withdraw money from a user's account.
     * @param user The user to withdraw from
     * @param money The amount of money to withdraw.
     * @param id The id of the account to withdraw from.
     * @return true if the withdrawal succeeds, false otherwise.
     */
    boolean withdraw(User user, int money, int id) throws MoneyTransferException {
        for (Person person: this.users) {
            if (person == user) {
                if (person instanceof User) {
                    Account account;
                    if (id < 0) {
                        account = ((User)person).getPrimaryAccount();
                    }
                    else {
                        account = ((User)person).getAccount(id);
                    }
                    if (account == null) {
                        return false;
                    }
                    Withdrawal withdrawal = new Withdrawal(-99, id, money);
                    account.processWithdrawal(withdrawal);
                    return true;
                }
            }
        }
        return false;
    }

    boolean deposit(User user, BigDecimal money, int id) throws MoneyTransferException {
        for (Person person: this.users)
            if (person == user) {
                if (person instanceof User) {
                    Account account = null;
                    if (id < 0) account = ((User) person).getPrimaryAccount();
                    else account = ((User) person).getAccount(id);
                    if (account == null) return false;

                    //TODO: Handle all transactions this way!
                    Deposit deposit = new Deposit(-99, id, money);
                    account.processDeposit(deposit);

                    return true;
                }
            }
        return false;
    }

}
