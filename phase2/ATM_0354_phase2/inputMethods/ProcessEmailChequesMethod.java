package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;

import java.util.Scanner;

public class ProcessEmailChequesMethod  implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("Would you like to process the cheques that have been sent electronically (y/n)?\n>");
        String answer = in.nextLine();
        if (answer.equals("y")) {
            Main.atm.processEmailCheques();
            return "BankManagerOptions";
        } else if (answer.equals("n")) {
            return "BankManagerOptions";
        } else {
            System.out.println("Not a valid input. Try again.");
            return this.run(in);
        }
    }
}
