package ATM_0354.inputMethods;

import ATM_0354.InputMethod;

import java.util.Scanner;

public class setUpBankManagerMethod implements InputMethod {

    public boolean run(Scanner in){
        System.out.println("Create a login for the Bank Manager: ");
        System.out.print("Username: ");
        String username = in.nextLine();
        System.out.print("Password: ");
        String password = in.nextLine();
        System.out.println("Your username is: " + username +" and your password is: "+password);
        return true;
    }
}
