package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;

import java.util.Scanner;

public class AdminPanelMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("======= Admin Panel =======");
        System.out.println("Options:\nA. 'manage date' \nB. 'reset' the ATM.");
        System.out.println("What would you like to do?");
        System.out.print(">");
        String input = in.nextLine();
        while(true){
            switch(input.toLowerCase()){
                case "manage date":
                case "a":
                    System.out.println("Today's date is: "+Main.atm.getDateTime());
                    System.out.println("How many days would you like to move ahead?");
                    System.out.print(">");
                    int days = VerifyInputs.verifyInt(in);
                    Main.atm.newDate(Main.atm.getDateTime().plusDays(days));
                case "reset":
                case "b":
                    System.out.println("Are you sure you'd like to reset the ATM? (yes/no)");
                    System.out.print(">");
                    boolean reset = in.nextLine().equalsIgnoreCase("yes");
                    if(reset) return "ShutdownReset";
                    else{
                        return run(in);
                    }
                default:
                    System.out.println("Invalid option, please try again.");
                    System.out.print(">");
                    input = in.nextLine();
            }
        }
    }
}
