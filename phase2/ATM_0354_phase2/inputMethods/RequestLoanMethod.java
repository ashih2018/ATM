package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Loan;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.User;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RequestLoanMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("How much money would you like to loan out?");
        System.out.print(">");
        BigDecimal loanAmount = new BigDecimal(in.nextInt());
        System.out.println("Which account (id) would you like to loan into?");
        int id = -1;
        while(true) {
            try {
                boolean flag;
                do {
                    System.out.print(">");
                    try
                    {
                        id = in.nextInt();
                        flag=true;
                    }
                    catch (InputMismatchException exception)
                    {
                        System.out.println("Not a numerical value.");
                        in.nextLine();
                        flag = false;
                    }
                } while(!flag);
                boolean idExists = ((User) Main.atm.getCurUser()).verifyID(id);
                if (idExists)
                    break;
                else{
                    System.out.println("Invalid id");
                    System.out.println("Which account (id) would you like to loan into?");
                }
            } catch (ClassCastException e) {
                System.out.println("Invalid id");
                System.out.println("Which account (id) would you like to loan into?");
                System.out.print(">");
            }
        }
        Loan loan = new Loan((((User) Main.atm.getCurUser()).getAccount(id)), loanAmount, new BigDecimal(4));
        loan.process();
        in.nextLine();
        return "UserOptions";
    }
}
