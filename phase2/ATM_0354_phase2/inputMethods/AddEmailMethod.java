package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.EmailHandler;
import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;

import java.util.Scanner;


public class AddEmailMethod implements InputMethod {

    @Override
    public String run(Scanner in) {
        System.out.println("What would you like to set your email to?");
        System.out.print(">");
        String email = VerifyInputs.verifyEmailFormat(in);
        System.out.println("An email will be sent to the account to verify.");
        int securityNum = EmailHandler.verifyEmail(Main.atm.getCurUser(), email);
        if (securityNum == -1) {
            System.out.println("There was an error sending the email. Please try again later.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "UserOptions";
        } else {
            while (true) {
                System.out.println("Input your security number.");
                System.out.print(">");
                int attempt = VerifyInputs.verifyInt(in, false);
                if (securityNum == attempt) {
                    System.out.println("Correct security number. Your email has been verified.");
                    Main.atm.getCurUser().setEmail(email);
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "UserOptions";
                } else {
                    System.out.println("Incorrect security number. Would you like to try again? (yes/no)");
                    System.out.print(">");
                    if (VerifyInputs.verifyConfirmation(in)) break;
                }
            }
        }
        return null;
    }

}
