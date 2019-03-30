package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.BankManager;
import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;


import java.util.Scanner;

public class RefillCashMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("======== Refill Cash ========");
        while (true) {
            System.out.println(Main.atm.getCashSummary());
            System.out.println("What denomination would you like to restock?");
            System.out.print(">");
            int denomination = VerifyInputs.verifyDenomination(in);
            System.out.println("How many of those bills would you like to add?");
            System.out.print(">");
            int count = VerifyInputs.verifyInt(in);
            Main.atm.addCash(denomination, count);
            System.out.println("Added " + count + " bills of denomination " + denomination);
            System.out.println("Would you like to restock again? ('yes'/other)");
            System.out.print(">");
            boolean repeat = in.nextLine().equalsIgnoreCase("yes");
            if (!repeat) {
                if(Main.atm.getCurUser() instanceof BankManager)
                    return "BankManagerOptions";
                else
                    return "BankEmployeeOptions";
            }
        }
    }
}
