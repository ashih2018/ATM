package ATM_0354.inputMethods;

import ATM_0354.InputMethod;
import ATM_0354.Main;

import java.util.Scanner;

public class LoginMethod implements InputMethod {
    public String run(Scanner in){
        System.out.print("Username: ");
        String username = in.nextLine();
        System.out.print("Password: ");
        String password = in.nextLine();
        Main.atm.signIn(username, password);
        return "";
    }

}
