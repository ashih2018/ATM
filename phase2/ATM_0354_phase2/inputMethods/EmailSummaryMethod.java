package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.User;

import java.util.Scanner;

public class EmailSummaryMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.print("Type the email that you want the account summary sent to, or type 'exit' to cancel:\n>");
        String email = in.nextLine();
        if (email.equals("exit")) return "UserOptions";
        System.out.print("Is " + email + " the correct email (yes/no)?\n>");
        if (VerifyInputs.verifyConfirmation(in)) {
            System.out.println("Sending email to " + email);
            Main.atm.sendEmailSummary((User) Main.atm.getCurUser(), email);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "UserOptions";
        } else {
            System.out.println("Try again.");
            return this.run(in);
        }
    }
}
