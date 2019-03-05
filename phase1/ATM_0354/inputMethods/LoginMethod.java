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
        if(Main.atm.signIn(username, password)){
            if(username.equals("admin")){ //TODO: replace with a more sophisticated check if the user is a BankEmployee
                return "BankManagerOptions";
            }
            else{
                return "UserOptions";
            }
        }
        else{
            System.out.println("Incorrect Login, try again.");
            this.run(in); //TODO: verify that this won't StackOverflow under reasonable circumstances
        }
        return "";
    }

}
