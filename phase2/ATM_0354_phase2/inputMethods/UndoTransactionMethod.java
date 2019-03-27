package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.*;

import java.util.Scanner;

public class UndoTransactionMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("========== Undo Transaction ==========");
        System.out.println("Enter username of User that wants to undo transaction, or type . to return to main screen");
        String username = in.nextLine();

        if (username.equals(".")) return "BankManagerOptions";

        Person currUser = Main.atm.getUser(username);
        if (currUser != null) {
            System.out.println("These are the available accounts.");
            System.out.println("Type the account (id) to undo transaction for that account:");
            System.out.println(((User) currUser).getSummary());
            int accountId;
            try {
                accountId = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("That was not a number, lets try this again.");
                return this.run(in);
            }

            try {
                ((BankManager) Main.atm.getCurUser()).undoRecentTransaction((User) currUser, accountId);
            } catch (MoneyTransferException e) {
                e.printStackTrace();
                return "BankManagerOptions";
            }

            System.out.println("Successful undo of transaction!");

            return "BankManagerOptions";

        } else {
            System.out.println("Incorrect User with such a username does not exist, try again");
            return this.run(in);
        }

    }
}
