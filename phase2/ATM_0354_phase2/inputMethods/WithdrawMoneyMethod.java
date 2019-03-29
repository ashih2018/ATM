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
            System.out.println(accountSummary.equals("") ? "No accounts" : accountSummary); //I love ternary operators
            if (accountSummary.equals("")) {
                System.out.println("Input anything to go back.");
                System.out.print(">");
                in.nextLine();
                return "UserOptions";
            } else {
                int id = VerifyInputs.verifyAccountId(in, curUser, "withdraw from");
                int money = askForMoney(in);
                Transaction withdrawal = new Withdrawal(curUser.getAccount(id), money);
                withdrawal.process();
                System.out.println("Would you like to withdraw more money? (Y/N)");
                System.out.print(">");
                String response = in.nextLine();
                boolean cont = response.equals("yes") || response.equalsIgnoreCase("Y");
                if (!cont) return "UserOptions";
            }
        }
    }

    private int askForMoney(Scanner in){
        System.out.println("How much money would you like to withdraw?");
        System.out.print(">");
        int money;
        try{
            money = in.nextInt();
        } catch(ClassCastException e){
            System.out.println("Invalid amount");
            return askForMoney(in);
        }
        return money;
    }
}
