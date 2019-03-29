package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.User;

import java.util.Scanner;

public class EmailSummaryMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("Type the email that you want the account summary sent to:\n>");
        String email = in.nextLine();
        System.out.println("Is " + email +" the correct email (y/n)?\n>");
        String answer = in.nextLine();
        if (answer.equals("y")) {
            Main.atm.sendEmailSummary((User) Main.atm.getCurUser(), email);
            System.out.println("Email sent!");
            return "UserOptions";
        } else {
            System.out.println("Lets try again");
            return this.run(in);
        }
    }
}
