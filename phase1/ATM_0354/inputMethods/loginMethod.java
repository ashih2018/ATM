package ATM_0354.inputMethods;

import ATM_0354.InputMethod;
import ATM_0354.Main;

import java.util.Scanner;

public class loginMethod implements InputMethod {
    public boolean run(Scanner in){
        System.out.print("Username: ");
        String username = in.nextLine();
        System.out.print("Password: ");
        String password = in.nextLine();
        return Main.atm.signIn(username, password);
    }

}
