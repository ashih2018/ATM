package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AdminPanelMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("======= Admin Panel =======");
        System.out.println("Options:\nA. 'manage date' \nB. 'reset' the ATM.\nC. Go 'back'");
        System.out.println("What would you like to do?");
        System.out.print(">");
        String input = in.nextLine();
        while(true){
            switch(input.toLowerCase()){
                case "manage date":
                case "a":
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    System.out.println("Today's date is: "+Main.atm.getDateTime().format(formatter));
                    System.out.println("How many days would you like to move ahead?");
                    System.out.print(">");
                    int days = VerifyInputs.verifyInt(in, true);
                    Main.atm.newDate(Main.atm.getDateTime().plusDays(days));
                    return "BankManagerOptions";
                case "reset":
                case "b":
                    System.out.println("Are you sure you'd like to reset the ATM? (yes/no)");
                    System.out.print(">");
                    if(VerifyInputs.verifyConfirmation(in)) return "ShutdownReset";
                    else return run(in);
                case "back":
                case "c":
                    return "BankManagerOptions";
                default:
                    System.out.println("Invalid option, please try again.");
                    System.out.print(">");
                    input = in.nextLine();
            }
        }
    }
}
