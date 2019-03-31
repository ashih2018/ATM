package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.*;

import java.util.Scanner;

public class WithdrawMoneyMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("======= Withdraw Money =======");
        while(true) {
            System.out.println("==== Your Accounts ====");
            String accountSummary = ((User) Main.atm.getCurUser()).getSummary();
            User curUser = (User) Main.atm.getCurUser();
            System.out.println(accountSummary.equals("") ? "No accounts" : accountSummary);
            if (accountSummary.equals("")) {
                System.out.println("Input anything to go back.");
                System.out.print(">");
                in.nextLine();
                return "UserOptions";
            } else {
                System.out.println("Which account would you like to withdraw from?");
                System.out.print(">");
                int id = VerifyInputs.verifyAccountId(in, curUser, "withdraw from");
                System.out.println("How much money would you like to withdraw?");
                System.out.print(">");
                int money = VerifyInputs.verifyInt(in);
                Transaction withdrawal = new Withdrawal(curUser.getAccount(id), money);
                withdrawal.process();
                System.out.println("Would you like to withdraw more money? (Y/N)");
                System.out.print(">");
                if (!VerifyInputs.verifyConfirmation(in)) return "UserOptions";
            }
        }
    }

}
