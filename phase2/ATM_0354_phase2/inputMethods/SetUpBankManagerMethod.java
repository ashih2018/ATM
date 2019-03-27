package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;

import java.util.Scanner;
import java.util.regex.Pattern;

public class SetUpBankManagerMethod implements InputMethod {

    public String run(Scanner in){
        System.out.println("Create a login for the Bank Manager: ");
        System.out.println("Create a Username. Only use alphanumeric characters: ");
        System.out.print(">");
        String username =VerifyInputs.verifyNewUsername(in);
        System.out.println("Create a Password. Only use alphanumeric characters: ");
        System.out.print(">");
        String password =VerifyInputs.verifyNewPassword(in);
        System.out.println("Your username is: " + username +" and your password is: "+password);
        Main.atm.createPerson("BankManager", username, password, null);
        return "Login";
    }
}
