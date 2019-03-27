package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.BankManager;
import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.Person;

import java.util.Scanner;

public class LoginMethod implements InputMethod {
    public String run(Scanner in){
        System.out.println("========== Login ==========");
        System.out.print("Username: ");
        String username = in.nextLine();
        System.out.print("Password: ");
        String password = in.nextLine();
        Person curUser = Main.atm.signIn(username, password);
        if(curUser != null){
            if(curUser instanceof BankManager){ //TODO: replace with a more sophisticated check if the user is a BankEmployee
                return "BankManagerOptions";
            }
            else{
                return "UserOptions";
            }
        }
        else{
            System.out.println("Incorrect Login, try again.");
            return this.run(in);
        }
    }

}
