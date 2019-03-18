package ATM_0354.inputMethods;

import ATM_0354.InputMethod;
import ATM_0354.Main;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class RequestNewAccountMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("======= New Account Request =======");
        while(true) {
            System.out.println("What kind of account would you like? You can open a 'credit card' account, 'line of credit' account, 'chequing' account, or 'savings' account.");
            System.out.print(">");
            String input = verifyAccountType(in);
            Main.atm.requestCurAccount(input);
            System.out.print("Would you like to request to open another account? (yes/no) \n>");
            boolean cont = in.nextLine().equals("yes");
            if(!cont) break;
        }
        return "UserOptions";

    }
    private String verifyAccountType(Scanner in){
        String input = in.nextLine();
        if(new HashSet<>(Arrays.asList("credit card", "line of credit", "chequing", "savings")).contains(input)){
            return input;
        }
        System.out.println("Invalid account type.");
        System.out.print(">");
        return verifyAccountType(in);
    }
}
