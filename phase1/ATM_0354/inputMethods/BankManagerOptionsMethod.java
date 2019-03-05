package ATM_0354.inputMethods;

import ATM_0354.InputMethod;

import java.util.Scanner;

public class BankManagerOptionsMethod implements InputMethod {
    /**
     * Display the BankManager Options Screen.
     * @param in The Scanner Object
     * @return The state that should be moved to next
     */
    @Override
    public String run(Scanner in) {
        System.out.println("You can 'create users', 'view account creation requests,' 'refill cash,' or 'undo recent transactions'");
        System.out.println("What would you like to do?");
        System.out.print(">");
        String input = in.nextLine();
        while(true) {
            switch (input) {
                case "create users":
                    return "userCreationScreen";
                case "view account creation requests":
                    return "accountCreationRequest";
                case "refill cash":
                    return "refillCash";
                case "undo recent transactions":
                    return "undoTransaction";
                default:
                    System.out.println("Invalid option, please try again.");
                    System.out.print(">");
                    input = in.nextLine();
            }
        }
    }
}
