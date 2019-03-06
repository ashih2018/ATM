package ATM_0354.inputMethods;

import ATM_0354.InputMethod;
import ATM_0354.Main;
import ATM_0354.User;

import java.math.BigDecimal;
import java.util.Scanner;

public class DepositMoneyMethod implements InputMethod {

    @Override
    public String run(Scanner in) {
        System.out.println("======= Deposit Money =======");
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
                System.out.println("Which account (id) would you like to deposit into?");
                System.out.print(">");
                int id;
                while(true) {
                    try {
                        id = in.nextInt();
                        break;
                    } catch (ClassCastException e) {
                        System.out.println("Invalid id");
                        System.out.println("Which account (id) would you like to deposit into?");
                        System.out.print(">");
                    }
                }
                System.out.println("Depositing from deposits.txt....");
//                BigDecimal money = Main.parseDeposits();
//                Main.atm.deposit((User) Main.atm.getCurUser(), money, id);
                System.out.println("Would you like to deposit more money?");
                System.out.print(">");
                boolean cont = in.nextLine().equals("yes");
                if (!cont) {
                    return "UserOptions";
                }
            }
        }
    }

}
