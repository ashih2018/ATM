package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.User;

import java.util.Scanner;

public class EmailSummaryMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        User curUser = (User) Main.atm.getCurUser();
        if(curUser.hasEmail()){
            System.out.println(String.format("Your default email is %s. Would you like to send a summary to that email?", curUser.getEmail()));
            System.out.print(">");
            if(VerifyInputs.verifyConfirmation(in)){
                System.out.println(String.format("Sending email to %s", curUser.getEmail()));
                Main.atm.sendEmailSummary(curUser, curUser.getEmail());
                System.out.println("Canceling summary....");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "UserOptions";
            }
            else{
                System.out.println("Canceling summary....");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "UserOptions";
            }
        }

        System.out.print("Type the email that you want the account summary sent to, or type 'exit' to cancel:\n>");
        String email = in.nextLine();
        if (email.equals("exit")) return "UserOptions";
        System.out.print("Is " + email + " the correct email (yes/no)?\n>");
        if (VerifyInputs.verifyConfirmation(in)) {
            System.out.println("Sending email to " + email);
            Main.atm.sendEmailSummary(curUser, email);
            return "UserOptions";
        } else {
            System.out.println("Try again.");
            return this.run(in);
        }
    }
}
