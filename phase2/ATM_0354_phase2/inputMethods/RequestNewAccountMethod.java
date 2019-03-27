package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.User;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class RequestNewAccountMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("======= New Account Request =======");
        do {

            System.out.println("Would you like to open a joint account or an individual account? (joint/individual)");
            System.out.print(">");
            String response = in.nextLine();
            boolean joint = response.equals("joint");
            while (!response.equals("joint") && !response.equals("individual")) {
                System.out.println("Would you like to open a joint account or an individual account? (joint/individual)");
                System.out.print(">");
                response = in.nextLine();
                joint = response.equals("joint");
            }
            User otherUser = null;
            System.out.println(joint);
            if (joint) {
                System.out.println("Who would you like to open the account with?");
                System.out.print(">");
                otherUser = verifyUser(in);
            }
            System.out.println("What kind of account would you like? You can open a 'credit card' account, 'line of credit' account, 'chequing' account, or 'savings' account.");
            System.out.print(">");
            String input = verifyAccountType(in);
            if (joint) Main.atm.requestJointAccount((User) Main.atm.getCurUser(), otherUser, input);
            else Main.atm.requestCurAccount(input);
            System.out.print("Would you like to request to open another account? (yes/no) \n>");
        } while (in.nextLine().equals("yes"));
        return "UserOptions";

    }

    private User verifyUser(Scanner in) {
        String input = in.nextLine();
        if (Main.atm.usernameExists(input) && Main.atm.getUser(input) instanceof User)
            return (User) Main.atm.getUser(input);

        System.out.println("Not a valid username.");
        System.out.print(">");
        return verifyUser(in);
    }

    private String verifyAccountType(Scanner in) {
        String input = in.nextLine();
        if (new HashSet<>(Arrays.asList("credit card", "line of credit", "chequing", "savings")).contains(input)) {
            return input;
        }
        System.out.println("Invalid account type.");
        System.out.print(">");
        return verifyAccountType(in);
    }
}
