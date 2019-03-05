package ATM_0354;

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
    //TODO: Docstring + figure out where createUser should be
    public void createUser(String type, String username, String password){
        if(type.equals("BankManager")){
            users.add(new BankDaddy(username, password));
        }
        else{
            users.add(new User(username, password));
        }
    }

    public boolean usernameExists(String username){
        for (Person person : users){
            if (person.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

}
