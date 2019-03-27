package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.BankEmployee;
import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;

import java.util.Scanner;

public class UserOptionsMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("======= User Panel ========");
        boolean isEmployee = Main.atm.getCurUser() instanceof BankEmployee;
        System.out.println("Options:");
        if (!isEmployee) {
            System.out.println("A. 'view accounts'\nB. 'withdraw money'\nC. 'deposit money' " +
                    "\nD. 'transfer money' \nE. 'logout'");
        }
        else{
            System.out.println("A. 'view accounts'\nB. 'withdraw money'\nC. 'deposit money' " +
                    "\nD. 'transfer money' \nE. 'logout'\nF. 'view employee options'");
        }
        System.out.println("What would you like to do?");
        System.out.print(">");
        String input = in.nextLine();
        while (true) {
            switch (input.toLowerCase()) {
                case "view accounts":
                case "a":
                    return "AccountView";
                case "withdraw money":
                case "b":
                    return "WithdrawMoney";
                case "deposit money":
                case "c":
                    return "DepositMoney";
                case "transfer money":
                case "d":
                    return "TransferMoney";
//                case "pay bills":
//                    return "PayBills";
                case "logout":
                case "e":
                    return "Logout";
                case "view employee options":
                case "f":
                    if (isEmployee) return "EmployeeOptions";
                    else return "UserOptions";
                default:
                    System.out.println("Invalid option, please try again.");
                    System.out.print(">");
                    input = in.nextLine();
            }
        }
    }
}
