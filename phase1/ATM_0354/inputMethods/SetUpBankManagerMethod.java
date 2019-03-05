package ATM_0354.inputMethods;

import ATM_0354.InputMethod;
import ATM_0354.Main;

import java.util.Scanner;

public class SetUpBankManagerMethod implements InputMethod {

    public String run(Scanner in){
        System.out.println("Create a login for the Bank Manager: ");
        System.out.print("Username: ");
        String username = in.nextLine();
        System.out.print("Password: ");
        String password = in.nextLine();
        System.out.println("Your username is: " + username +" and your password is: "+password);
        Main.atm.createPerson("BankManager", username, password);
        return "Login";
    }
}
