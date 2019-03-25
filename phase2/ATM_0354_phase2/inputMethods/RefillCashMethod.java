package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;

import java.math.BigDecimal;
import java.util.Scanner;

public class RefillCashMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        int denomination = 0;
        while (true) {
            while (true) {
                try {
                    System.out.println("What denomination would you like to restock?");
                    System.out.println(">");
                    denomination = in.nextInt();
                    if (denomination <= 0) {
                        System.out.println("That is not a valid denomination. \n Please enter a new denomination.");
                        System.out.println(">");
                    } else {
                        break;
                    }
                } catch (ClassCastException e) {
                    System.out.println("Invalid denomination.");
                    System.out.println("What denomination would you like to restock?");
                    System.out.println(">");
                }
            }

            while (true) {
                try {
                    System.out.println("How many of those bills would you like to add?");
                    System.out.println(">");
                    int count = in.nextInt();
                    if (count <= 0) {
                        System.out.println("Please enter a positive number of bills.");
                        System.out.println(">");
                    } else {
                        Main.atm.addCash(denomination, count);
                        System.out.println("Added " + count + " bills of denomination " + denomination);
                        break;
                    }
                } catch (ClassCastException e) {
                    System.out.println("Invalid count. How many of those bills would you like to add?");
                    System.out.println(">");
                }
            }
            System.out.println("Would you like to restock again?");
            System.out.println(">");
            boolean repeat = in.nextLine().equals("yes");
            if (!repeat) return "BankManagerOptions";
        }
    }
}
