package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.User;

import java.math.BigDecimal;
import java.util.Scanner;

public class PayLoansMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("======= Pay Loans ======");
        User curUser = (User) Main.atm.getCurUser();
        String summary = curUser.loanSummary();
        if(summary.equals("")){
            System.out.println("No loans! Input anything to go back.");
            System.out.print(">");
            in.nextLine();
            return "UserOptions";
        }
        int numLoans = Integer.parseInt(summary.split(";")[0]);
        summary = summary.split(";")[1];
        System.out.println(summary);
        System.out.println("What loan would you like to pay off?");
        System.out.print(">");
        int loanId;
        do {
            loanId = VerifyInputs.verifyInt(in);
        } while (loanId >= numLoans);
        System.out.println("How much would you like to pay back?");
        System.out.print(">");
        BigDecimal amount = BigDecimal.valueOf(VerifyInputs.verifyDouble(in));
        while (!curUser.payLoan(amount, loanId)) {
            System.out.println("How much would you like to pay back?");
            System.out.print(">");
            amount = BigDecimal.valueOf(VerifyInputs.verifyDouble(in));
        }
        System.out.println("Would you like to pay back more loans?");
        System.out.print(">");
        boolean cont = in.nextLine().equalsIgnoreCase("yes");
        if(cont) return run(in);
        return "UserOptions";
    }
}
