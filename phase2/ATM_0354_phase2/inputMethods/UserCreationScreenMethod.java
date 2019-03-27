package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.ATM;
import ATM_0354_phase2.BankManager;
import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;

import java.util.Scanner;
import java.util.regex.Pattern;

public class UserCreationScreenMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("========== User Creation ==========");
        while (true) {
            System.out.println("What type of user are you creating?\nA. Customer\nB. Employee");
            System.out.println(">");
            String type = in.nextLine();
            while(true){
                if (type.equalsIgnoreCase("customer") || type.equalsIgnoreCase("a")){
                    type = "User";
                    break;
                }
                else if (type.equalsIgnoreCase("employee") || type.equalsIgnoreCase("b")){
                    type = "BankEmployee";
                    break;
                }
                else{
                    System.out.println("That is not a valid type.");
                    System.out.println("Please enter\nA. User\nB. Employee");
                    System.out.println(">");
                    type = in.nextLine();
                }
            }
            System.out.println("What is the new user's username? Use only alphanumeric characters.");
            System.out.print(">");
            String username = VerifyInputs.verifyNewUsername(in);
            System.out.println("That is a valid username.");
            System.out.println("What is the new user's password? Use only alphanumeric characters.");
            System.out.print(">");
            String password = VerifyInputs.verifyNewPassword(in);
            Main.atm.createPerson(type, username, password, null);
            System.out.println("Would you like to create another user? (yes/no)");
            System.out.print(">");
            boolean anotherUser = in.nextLine().equals("yes");
            if (!anotherUser) break;
        }
        if(Main.atm.getCurUser() instanceof BankManager)
            return "BankManagerOptions";
        else
            return "BankEmployeeOptions";
    }
}
