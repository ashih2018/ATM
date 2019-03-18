package ATM_0354_phase1.inputMethods;

import ATM_0354_phase2.InputMethod;

import java.util.Scanner;

public class BankManagerOptionsMethod implements InputMethod {
    /**
     * Display the BankManager Options Screen.
     *
     * @param in The Scanner Object
     * @return The state that should be moved to next
     */
    @Override
    public String run(Scanner in) {
        System.out.println("======= Bank Manager Panel ========");
        System.out.println("You can 'create users', 'approve account creation requests', 'refill cash', 'undo recent transactions', 'logout', or 'shutdown'");
        System.out.println("What would you like to do?");
        System.out.print(">");
        String input = in.nextLine();
        while (true) {
            switch (input) {
                case "create users":
                    return "UserCreationScreen";
                case "approve account creation requests":
                    return "IndividualAccountApprove";
                case "refill cash":
                    return "RefillCash";
                case "undo recent transactions":
                    return "UndoTransaction";
                case "logout":
                    return "Logout";
                case "shutdown":
                    return "Shutdown";
                default:
                    System.out.println("Invalid option, please try again.");
                    System.out.print(">");
                    input = in.nextLine();
            }
        }
    }
}
