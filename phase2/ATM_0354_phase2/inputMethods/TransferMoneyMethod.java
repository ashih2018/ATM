package ATM_0354_phase2.inputMethods;


import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.User;

import java.util.Scanner;

public class TransferMoneyMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("======= Transfer Money =======");
        User curUser = (User) Main.atm.getCurUser();
        while (true) {
            System.out.println("==== Your Accounts ====");
            String accountSummary = Main.atm.viewCurAccounts();
            System.out.println(accountSummary.equals("") ? "No accounts" : accountSummary);
            if (accountSummary.equals("")) {
                System.out.println("Input anything to go back.");
                System.out.print(">");
                in.nextLine();
                return "UserOptions";
            } else {
                System.out.println("Which account (id) would you like to transfer from?");
                System.out.print(">");
                int id = VerifyInputs.verifyAccountId(in, curUser, "transfer from");
                System.out.println("Would you like to transfer to your own account or a different user's account?");
                System.out.println("Press \'A\' for your own account. \nPress \'B\' for a different user's account.");
                System.out.print(">");
                while (true) {
                    String input = in.nextLine();
                    if (input.equalsIgnoreCase("a")) {
                        HelperFunctions.transferToOwnAccount(id, in);
                        break;
                    } else if (input.equalsIgnoreCase("b")) {
                        HelperFunctions.transferToOtherUser(id, in);
                        break;
                    } else {
                        System.out.println("Please enter \'A\' for your own account or \'B\' for a different " +
                                "user's account.");
                        System.out.print(">");
                    }
                }
                System.out.println("Would you like to transfer more money?");
                System.out.print(">");
                boolean cont = in.nextLine().equals("yes");
                if (!cont) {
                    return "UserOptions";
                }
            }
        }
    }
}
