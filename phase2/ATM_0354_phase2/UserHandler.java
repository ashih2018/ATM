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
     *
     * @param username The inputted username
     * @param password The inputted password
     * @return the person with this login information, or null if not found
     */
    public Person verifyUser(String username, String password) {
        for (Person user : users) {
            if (user.getUsername().equals(username)) {
                if (!user.getHash().equals(PasswordHash.hashPassword(password, user.getSalt()))) {
                    System.out.println("Incorrect Password");
                    System.out.println(user.getUsername() + " " + user.getHash() + " " + user.getSalt());
                    return null;
                } else {
                    return user;
                }
            }
        }
        System.out.println("User not found");
        return null;
    }

    public static boolean verifyPassword(Person user, String password) {
        return user.getHash().equals(PasswordHash.hashPassword(password, user.getSalt()));
    }

    /**
     * Create a new User.
     * Precondition: The username of the User is unique.
     *
     * @param type     The type of the new User
     * @param username The username of the new User.
     * @param password The password of the new User.
     */
    public void createUser(String type, String username, String password, String salt) {
        if (type.equals("BankManager")) {
            users.add(new BankManager(username, password, salt));
        } else if (type.equals("BankEmployee")) {
            users.add(new BankEmployee(username, password, salt));
        } else {
            users.add(new User(username, password, salt));
        }
    }

    /**
     * Check whether or not a username already exists.
     *
     * @param username The username to check
     * @return True if the username exists, false otherwise.
     */
    public boolean usernameExists(String username) {
        for (Person person : users) {
            if (person.getUsername().equals(username)) {
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
     *
     * @param user  The user to withdraw from
     * @param money The amount of money to withdraw.
     * @param id    The id of the account to withdraw from.
     * @return true if the withdrawal succeeds, false otherwise.
     */
    boolean withdraw(User user, int money, int id) throws MoneyTransferException {
        for (Person person : this.users) {
            if (person == user) {
                if (person instanceof User) {
                    Account account = processAccountID(person, id);
                    if (account == null) return false;
                    Withdrawal withdrawal = new Withdrawal(account, money);
                    withdrawal.process();
                    return true;
                }
            }
        }
        return false;
    }

    private Account processAccountID(Person person, int id) {
        if (id < 0) return ((User) person).getPrimaryAccount();
        else return ((User) person).getAccount(id);
    }

    boolean deposit(User user, BigDecimal money, int id) {
        for (Person person : this.users)
            if (person == user) {
                if (person instanceof User) {
                    Account account = processAccountID(person, id);
                    if (account == null) return false;
                    Deposit deposit = new Deposit(account, money);
                    deposit.process();

                    return true;
                }
            }
        return false;
    }

    void newMonth(int deltaMonths) {
        for (Person user : this.users)
            if (user instanceof User) {
                User curUser = (User) user;
                curUser.newMonth(deltaMonths);
            }
    }

}
