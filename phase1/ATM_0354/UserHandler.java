package ATM_0354;

import java.io.*;
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

    /**
     * Create a new User.
     * Precondition: The username of the User is unique.
     * @param type The type of the new User
     * @param username The username of the new User.
     * @param password The password of the new User.
     */
    public void createUser(String type, String username, String password){
        String entry;
        if(type.equals("BankManager")){
            users.add(new BankDaddy(username, password));
            entry = "BankManager," + username + "," + password;
        }
        else{
            users.add(new User(username, password));
            entry = "User, " + username + "," + password;
        }
        String path = "phase1/ATM_0354/Files/people.txt";
        BufferedWriter writer;
        try{
            File file = new File(path);
            writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(entry);
            writer.newLine();
            writer.close();
        }
        catch (IOException e){
            System.out.println(e.toString());
            System.out.println("IOException when writing to people.txt to create users.");
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

    public User getUserFromAccountId(int accountId) {
        for (Person person : users) {
            if (person instanceof User) {
                Account account = ((User) person).getAccount(accountId);
                if (account != null) return (User) person;
            }
        } return null;
    }

    /**
     * Withdraw money from a user's account.
     * @param user The user to withdraw from
     * @param money The amount of money to withdraw.
     * @param id The id of the account to withdraw from.
     * @return true if the withdrawal succeeds, false otherwise.
     */
    boolean withdraw(User user, BigDecimal money, int id){
        //TODO: Complete
        return false;
    }

    boolean deposit(User user, BigDecimal money, int id){
        //TODO: Complete
        return false;
    }

}
