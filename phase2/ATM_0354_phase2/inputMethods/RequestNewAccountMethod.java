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
            if (joint) {
                System.out.println("Who would you like to open the account with?");
                System.out.print(">");
                otherUser = VerifyInputs.verifyUser(in);
            }
            System.out.println("What kind of account would you like? You can open a 'credit card' account, 'line of credit' account, 'chequing' account, or 'savings' account.");
            System.out.print(">");
            String input = VerifyInputs.verifyAccountType(in);
            if (joint) Main.atm.requestJointAccount((User) Main.atm.getCurUser(), otherUser, input);
            else Main.atm.requestAccount((User) Main.atm.getCurUser(), input);
            System.out.print("Would you like to request to open another account? (yes/no) \n>");
        } while (in.nextLine().equals("yes"));
        return "UserOptions";

    }

}
