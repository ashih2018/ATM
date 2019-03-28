package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.User;

import java.math.BigDecimal;
import java.util.Scanner;

public class PayBillsMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.print("Who would you like to pay a bill to?\n>");
        User curUser = (User)Main.atm.getCurUser();
        String destination = in.nextLine();
        System.out.println("==== Your Accounts ====");
        String accountSummary = Main.atm.viewCurAccounts();
        System.out.println(accountSummary.equals("") ? "No accounts" : accountSummary);
        if (accountSummary.equals("")) {
            System.out.println("Input anything to go back.");
            System.out.print(">");
            in.nextLine();
            return "UserOptions";
        }
        System.out.print("Which account will you use to pay?\n>");
        int id = VerifyInputs.verifyAccountId(in, curUser);
        System.out.print("How much will you pay?\n>");
        BigDecimal amount = BigDecimal.valueOf(VerifyInputs.verifyDouble(in));
        curUser.payBill(destination, curUser.getAccount(id), amount);
        in.nextLine();
        return "UserOptions";
    }
}
