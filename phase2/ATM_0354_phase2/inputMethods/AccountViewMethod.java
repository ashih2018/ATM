package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;

import java.util.Scanner;

public class AccountViewMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        String accountSummary = Main.atm.viewCurAccounts();
        System.out.println("======= Account Summary =======");
        System.out.println("Total: "+Main.atm.getCurMoney()+"\n");
        System.out.println(accountSummary.equals("")?"No accounts":accountSummary);
        // weird flex but ok sky.
        System.out.println("You can 'request account' or input anything else to go back");
        System.out.print(">");
        String input = in.nextLine();
        if(input.equals("request account")){
            return "RequestNewAccount";
        }
        return "UserOptions";
    }
}
