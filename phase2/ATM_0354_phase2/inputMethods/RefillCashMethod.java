package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.BankManager;
import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;

import java.util.Scanner;

public class RefillCashMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        int denomination = 0;
        while (true) {
            while (true) {
                try {
                    System.out.println("What denomination would you like to restock?");
                    System.out.print(">");
                    denomination = in.nextInt();
                    in.nextLine();
                    if (denomination <= 0) {
                        System.out.println("That is not a valid denomination. \n Please enter a new denomination.");
                        System.out.print(">");
                    } else {
                        break;
                    }
                } catch (ClassCastException e) {
                    System.out.println("Invalid denomination.");
                    System.out.println("What denomination would you like to restock?");
                    System.out.print(">");
                }
            }

            while (true) {
                try {
                    System.out.println("How many of those bills would you like to add?");
                    System.out.print(">");
                    int count = in.nextInt();
                    in.nextLine();
                    if (count <= 0) {
                        System.out.println("Please enter a positive number of bills.");
                        System.out.print(">");
                    } else {
                        Main.atm.addCash(denomination, count);
                        System.out.print("Added " + count + " bills of denomination " + denomination);
                        break;
                    }
                } catch (ClassCastException e) {
                    System.out.println("Invalid count. How many of those bills would you like to add?");
                    System.out.print(">");
                }
            }
            System.out.println("Would you like to restock again?");
            System.out.print(">");
            boolean repeat = in.nextLine().equals("yes");
            if (!repeat) {
                if(Main.atm.getCurUser() instanceof BankManager)
                    return "BankManagerOptions";
                else
                    return "BankEmployeeOptions";
            }
        }
    }
}
