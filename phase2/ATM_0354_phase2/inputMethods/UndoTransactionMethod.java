package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.User;

import java.util.Scanner;

public class UndoTransactionMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("========== Undo Transaction ==========");
        System.out.println("Enter username of the User whose transactions you want to undo.");
        System.out.println("Input -1 to go back");
        System.out.print(">");
        User curUser = VerifyInputs.verifyUser(in);
        if (curUser == null) {
            return "BankManagerOptions";
        }
        System.out.println("===== Transaction History =====");
        String summaryOutput = curUser.fullTransactionHistory();
        if(summaryOutput.equals("")){
            System.out.println("No available transactions. Press anything to go back.");
            System.out.print(">");
            return "BankManagerOptions";
        }
        int amount = Integer.parseInt(summaryOutput.split(";")[0]);
        String summary = summaryOutput.split(";")[1];
        System.out.println(summary);
        System.out.println("How many transactions would you like to undo?");
        System.out.print(">");
        int num;
        while(true) {
            num = VerifyInputs.verifyInt(in, true);
            if(num <= amount) break;
            else{
                System.out.println("Invalid transaction number. Please try again.");
                System.out.print(">");
            }
        }
        System.out.println(String.format(num == 1?"Undoing %d transaction":"Undoing %d transactions", num));
        curUser.undoTransactions(num);
        System.out.println("Would you like to undo any more transactions for a different user? (yes/no)");
        System.out.print(">");
        if(VerifyInputs.verifyConfirmation(in)) return run(in);
        return "BankManagerOptions";
    }
}
