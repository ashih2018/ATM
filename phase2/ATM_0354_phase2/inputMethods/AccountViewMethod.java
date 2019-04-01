package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.Transaction;
import ATM_0354_phase2.User;

import java.util.Scanner;

public class AccountViewMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        String accountSummary = Main.atm.viewCurAccounts();
        System.out.println("======= Account Summary =======");
        System.out.println("Total: "+Main.atm.getCurMoney());
        System.out.println();
        System.out.println(accountSummary.equals("")?"No accounts":accountSummary);
        System.out.println("You can 'request account', 'view transaction history', or input anything else to go back");
        System.out.print(">");
        String input = in.nextLine();
        if(input.equals("request account"))
            return "RequestNewAccount";
        else if(input.equals("view transaction history"))
            viewTransactionHistory(in);
        return "UserOptions";
    }

    private void viewTransactionHistory(Scanner in){
        User user = (User) Main.atm.getCurUser();
        System.out.println("What account (id) would you like to view?");
        System.out.print(">");
        int id = VerifyInputs.verifyAccountId(in, user, "view");
        if(user.getAccount(id).getTransactions().isEmpty()){
            System.out.println("No transactions.");
        }
        for(Transaction transaction : user.getAccount(id).getTransactions()){
            System.out.println(transaction + "\n");
        }
        System.out.println("Input anything to go back.");
        in.nextLine();
        run(in);
    }
}
