package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.UserHandler;

import java.util.Scanner;

public class ChangePasswordMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("What is your current password?");
        while(true) {
            System.out.print(">");
            String password = in.nextLine();
            boolean correctPW = UserHandler.verifyPassword(Main.atm.getCurUser(), password);
            if (correctPW)
                break;
            else{
                System.out.println("Incorrect password, try again:");
            }
        }
        System.out.println("What would you like your new password to be?");
        System.out.print(">");
        String password1 = VerifyInputs.verifyNewPassword(in);
        Main.atm.getCurUser().setPassword(password1);
        return "UserOptions";
    }
}
