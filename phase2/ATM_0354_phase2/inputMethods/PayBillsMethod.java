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
        User curUser = (User) Main.atm.getCurUser();
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
        int id = VerifyInputs.verifyAccountId(in, curUser, "pay bills from");
        System.out.print("How much will you pay? Input a negative number to go back.\n>");
        BigDecimal amount;
        while (true) {
            amount = VerifyInputs.verifyMoney(in);
            if(amount.signum() == -1)
                return "UserOptions";
            if (curUser.getAccount(id).getBalance().compareTo(amount) < 0) {
                System.out.println("You don't have enough in that account!");
                System.out.println("Try a different amount or input a negative number to go back.");
                System.out.print(">");
            }
            else {
                break;
            }
        }
        System.out.println("$" + amount.toString() + " has been paid to " + destination + ".");
        curUser.payBill(destination, curUser.getAccount(id), amount);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "UserOptions";
    }
}
