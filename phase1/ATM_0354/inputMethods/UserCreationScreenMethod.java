package ATM_0354.inputMethods;

import ATM_0354.InputMethod;
import ATM_0354.Main;

import java.util.Scanner;

public class UserCreationScreenMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("========== User Creation ==========");
        while(true) {
            System.out.println("What is the new user's username?");
            System.out.print(">");
            String username = in.nextLine();
            System.out.println("What is the new user's password?");
            System.out.print(">");
            String password = in.nextLine();
            Main.atm.createPerson("User", username, password);
            System.out.println("Would you like to create another user? (yes/no)");
            System.out.print(">");
            boolean anotherUser = in.nextLine().equals("yes");
            if(!anotherUser) break;
        }
        return "BankManagerOptions";
    }
}
