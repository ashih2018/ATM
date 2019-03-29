package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.*;

import java.util.Scanner;

public class UndoTransactionMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("========== Undo Transaction ==========");
        System.out.println("Enter username of the User whose transactions you want to undo.");
        User curUser = VerifyInputs.verifyUser(in);
        System.out.println("===== Transaction History =====");
        System.out.println(curUser.transactionHistory());
        System.out.println("How many transactions would you like to undo?");
        System.out.print(">");
        int num = VerifyInputs.verifyInt(in);
        System.out.println(String.format(num == 1?"Undoing %d transaction":"Undoing %d transactions", num));
        curUser.undoTransactions(num);
        System.out.println("Would you like to undo any more transactions for a different user?");
        System.out.print(">");
        boolean cont = in.nextLine().equalsIgnoreCase("yes");
        if(cont) return run(in);
        return "BankManagerOptions";
    }
}
