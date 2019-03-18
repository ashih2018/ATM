package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;

import java.util.Scanner;
import java.util.regex.Pattern;

public class SetUpBankManagerMethod implements InputMethod {

    public String run(Scanner in){
        System.out.println("Create a login for the Bank Manager: ");
        System.out.println("Create a Username. Only use alphanumeric characters: ");
        System.out.print(">");
        String username = in.nextLine();
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");

        while (!p.matcher(username).matches() || Main.atm.usernameExists(username)) {
            if (!p.matcher(username).matches()) {
                System.out.println("Invalid character in username. Please only use alphanumeric characters (A-Z, a-z, 0-9)\n Please enter a different username");
                System.out.print(">");
                username = in.nextLine();
            } else if (Main.atm.usernameExists(username)) {
                System.out.println("That username already exists or is invalid. \n Please enter a different username.");
                System.out.print(">");
                username = in.nextLine();
            }
        }
        System.out.println("Create a Password. Only use alphanumeric characters: ");
        System.out.print(">");
        String password = in.nextLine();
        while (!p.matcher(password).matches()) {
            System.out.println("Invalid character in password. Please only use alphanumeric characters (A-Z, a-z, 0-9)\n Please enter a different username");
            System.out.print(">");
            password = in.nextLine();
        }

        System.out.println("Your username is: " + username +" and your password is: "+password);
        Main.atm.createPerson("BankManager", username, password);
        return "Login";
    }
}
