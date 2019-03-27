package ATM_0354_phase2.inputMethods;


import ATM_0354_phase2.Main;
import ATM_0354_phase2.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Contains methods to verify various input types
 */
public class VerifyInputs {
    static String verifyNewUsername(Scanner in){
        String username = in.nextLine();
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");

        if(p.matcher(username).matches() &&!Main.atm.usernameExists(username)){
            return username;
        }
        else if(!p.matcher(username).matches()) {
            System.out.println("Invalid character in username. Please only use alphanumeric characters (A-Z, a-z, 0-9)\n Please enter a different username");
            System.out.print(">");
            return verifyNewUsername(in);
        }
        else {
            System.out.println("That username already exists or is invalid. \n Please enter a different username.");
            System.out.print(">");
            return(verifyNewUsername(in));
        }

    }
    static String verifyNewPassword(Scanner in){
        String password = in.nextLine();
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");
        if(p.matcher(password).matches()){
            return password;
        }
        else{
            System.out.println("Invalid character in password. Please only use alphanumeric characters (A-Z, a-z, 0-9)\n Please enter a different username");
            System.out.print(">");
            return verifyNewPassword(in);
        }
    }
    static User verifyUser(Scanner in) {
        String input = in.nextLine();
        if (Main.atm.usernameExists(input) && Main.atm.getUser(input) instanceof User)
            return (User) Main.atm.getUser(input);

        System.out.println("Not a valid username.");
        System.out.print(">");
        return verifyUser(in);
    }
    static String verifyAccountType(Scanner in) {
        String input = in.nextLine();
        if (new HashSet<>(Arrays.asList("credit card", "line of credit", "chequing", "savings")).contains(input)) {
            return input;
        }
        System.out.println("Invalid account type.");
        System.out.print(">");
        return verifyAccountType(in);
    }
}
