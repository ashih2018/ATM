package ATM_0354.inputMethods;

import ATM_0354.InputMethod;
import ATM_0354.Main;

import java.util.Scanner;

public class AccountViewMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        String accountSummary = Main.atm.viewCurAccounts();
        System.out.println("======= Account Summary =======");
        System.out.println("Total: "+Main.atm.getCurMoney());
        System.out.println(accountSummary.equals("")?"No accounts":accountSummary); //I love ternary operators
        System.out.println("You can 'request account' or input anything else to go back");
        System.out.print(">");
        String input = in.nextLine();
        if(input.equals("request account")){
            return "RequestNewAccount";
        }
        return "UserOptions";
    }
}
