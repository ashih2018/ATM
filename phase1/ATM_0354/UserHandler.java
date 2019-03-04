package ATM_0354;

import java.util.ArrayList;

public class UserHandler {
    /**
     * An ArrayList that stores all users that are managed by this handler
     */
    ArrayList<User> users;

    public UserHandler() {
        users = new ArrayList<>();
    }

    /**
     * Verify if a user's login information is correct
     * @param username The inputted username
     * @param password The inputted password
     * @return true if the user exists and password is correct
     */
    public boolean verifyUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (!user.getPassword().equals(password)) {
                    System.err.println("Incorrect Password");
                    return false;
                } else
                    return true;

            }
        }
        System.err.println("User not found");
        return false;
    }

}
