package ATM_0354.inputMethods;

import ATM_0354.InputMethod;

import java.util.Scanner;

public class UserOptionsMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("======= User Panel ========");
        System.out.println("You can 'view accounts', 'withdraw money', 'deposit money', 'transfer money', 'pay bills', or 'logout'");
        System.out.println("What would you like to do?");
        System.out.print(">");
        String input = in.nextLine();
        while (true) {
            switch (input) {
                case "view accounts":
                    return "AccountView";
                case "withdraw money":
                    return "WithdrawMoney";
                case "deposit money":
                    return "DepositMoney";
                case "transfer money":
                    return "TransferMoney";
                case "pay bills":
                    return "PayBills";
                case "logout":
                    return "Logout";
                default:
                    System.out.println("Invalid option, please try again.");
                    System.out.print(">");
                    input = in.nextLine();
            }
        }
    }
}
