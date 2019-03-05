package ATM_0354.inputMethods;

import ATM_0354.InputMethod;
import ATM_0354.Main;
import ATM_0354.User;

import java.util.Scanner;

public class AccountViewMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        String accountSummary = ((User)Main.atm.getCurUser()).getSummary();
        System.out.println("======= Account Summary =======");
        System.out.println(accountSummary.equals("")?"No accounts":accountSummary); //I love ternary operators
        System.out.println("Input anything to go back");
        System.out.print(">");
        in.nextLine();
        return "UserOptions";
    }
}
