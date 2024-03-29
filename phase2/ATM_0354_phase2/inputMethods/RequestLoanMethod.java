package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Loan;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

public class RequestLoanMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("You can loan out " + ((User) Main.atm.getCurUser()).getLoanLimit());
        System.out.println("How much money would you like to loan out?");
        System.out.println("Input -1 to go back");
        System.out.print(">");
        BigDecimal loanAmount;
        while (true) {
            loanAmount = VerifyInputs.verifyMoney(in);
            if (loanAmount.intValue() == -1) {
                return "UserOptions";
            }
            else if (loanAmount.compareTo(BigDecimal.valueOf(((User) Main.atm.getCurUser()).getLoanLimit())) > 0) {
                System.out.println("You can't loan out that much!");
                System.out.print(">");
            }
            else {
                break;
            }
        }
        System.out.println("How many months would you like to loan for?");
        System.out.print(">");
        int months = VerifyInputs.verifyInt(in, true);
        System.out.println("Which account (id) would you like to loan into?");
        System.out.print(">");
        User curUser = (User) Main.atm.getCurUser();
        int id = VerifyInputs.verifyAccountId(in, curUser, "loan into");
        LocalDateTime tmpDate = Main.atm.getDateTime().plusMonths(months + 1);
        LocalDateTime endDate = LocalDateTime.of(tmpDate.getYear(), tmpDate.getMonth(), 1, 0, 0); //always ends on the first of the next month
        Loan loan = new Loan(curUser.getAccount(id), loanAmount, new BigDecimal(4), endDate);
        loan.process();
        ((User) Main.atm.getCurUser()).changeLoanLimit(-1 * loanAmount.intValue());
        System.out.println("The maximum amount you will be able to loan in the future is " + ((User) Main.atm.getCurUser()).getLoanLimit());
        System.out.println("Press any key to return");
        System.out.print(">");
        in.nextLine();
        return "UserOptions";
    }
}
