package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class PayLoansMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("======= Pay Loans ======");
        User curUser = (User) Main.atm.getCurUser();
        String summary = curUser.loanSummary();
        int numLoans = Integer.parseInt(summary.split(";")[0]);
        if(numLoans == 0){
            System.out.println("No loans! Input anything to go back.");
            System.out.print(">");
            in.nextLine();
            return "UserOptions";
        }
        summary = summary.split(";")[1];
        System.out.println(summary);
        System.out.println("What loan would you like to pay off? Input 9999 to go back.");
        System.out.print(">");
        int loanId;
        do {
            loanId = VerifyInputs.verifyInt(in);
        } while (loanId >= numLoans);
        if(loanId == 9999){
            return "UserOptions";
        }
        while(true) {
            System.out.println("How much would you like to pay back? Input a negative number to cancel.");
            System.out.print(">");
            BigDecimal amount = VerifyInputs.verifyMoney(in);
            System.out.println(curUser.getSummary());
            System.out.println("What account (id) would you like to pay back from?");
            System.out.print(">");
            int accountID = VerifyInputs.verifyAccountId(in, curUser, "pay back from");
            if (curUser.getAccount(accountID).canTransferOut()) {
                if (curUser.getAccount(accountID).getBalance().compareTo(amount) < 0) {
                    System.out.println("You don't have enough in that account!");
                } else {
                    while (!curUser.payLoan(amount, loanId)) {
                        System.out.println("How much would you like to pay back?");
                        System.out.print(">");
                        amount = VerifyInputs.verifyMoney(in);
                    }
                    curUser.getAccount(accountID).forceTransferOut(amount);
                    break;
                }
            }
            else{
                System.out.println("You can't pay back loans from that account!");
            }
        }
        System.out.println("Would you like to pay back more loans?");
        System.out.print(">");
        boolean cont = in.nextLine().equalsIgnoreCase("yes");
        if(cont) return run(in);
        return "UserOptions";
    }
}
