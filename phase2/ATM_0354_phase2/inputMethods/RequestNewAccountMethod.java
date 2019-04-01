package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.User;

import java.util.Scanner;

public class RequestNewAccountMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("======= New Account Request =======");
        do {
            boolean joint = false;
            if (Main.atm.numUsers() > 1) {
                System.out.println("Would you like to open a joint account or an individual account? (joint/individual)");
                System.out.print(">");
                String response = in.nextLine();
                joint = response.equals("joint");
                while (!response.equals("joint") && !response.equals("individual")) {
                    System.out.println("Would you like to open a joint account or an individual account? (joint/individual)");
                    System.out.print(">");
                    response = in.nextLine();
                    joint = response.equals("joint");
                }

            }
            User curUser = (User) Main.atm.getCurUser();
            User otherUser = null;
            if (joint) {
                while(true) {
                    System.out.println("Who would you like to open the account with?");
                    System.out.println("Input -1 to go back");
                    System.out.print(">");
                    otherUser = VerifyInputs.verifyUser(in);
                    if (otherUser == null) {
                        return "UserOptions";
                    }
                    if (otherUser == curUser) {
                        System.out.println("You can't open a joint account with yourself! Please try again.");
                    }else{
                        break;
                    }
                }
            }
            System.out.println("What kind of account would you like? You can open a 'credit card' account, 'line of credit' account, 'chequing' account, or 'savings' account.");
            System.out.print(">");
            String input = VerifyInputs.verifyAccountType(in);
            if (joint) Main.atm.requestJointAccount((User) Main.atm.getCurUser(), otherUser, input);
            else Main.atm.requestAccount((User) Main.atm.getCurUser(), input);
            System.out.print("Would you like to request to open another account? (yes/no) \n>");
        } while (VerifyInputs.verifyConfirmation(in));
        return "UserOptions";

    }

}
