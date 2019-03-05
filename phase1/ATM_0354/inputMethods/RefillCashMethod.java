package ATM_0354.inputMethods;

import ATM_0354.InputMethod;
import ATM_0354.Main;

import java.math.BigDecimal;
import java.util.Scanner;

public class RefillCashMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        while(true){
            System.out.println("What denomination would you like to restock?");
            BigDecimal denomination = in.nextBigDecimal();
            while (denomination == null){
                System.out.println("That is not a valid denomination. \n Please enter a new denomination.");
                denomination = in.nextBigDecimal();
            }
            System.out.println("How many of those bills would you like to add?");
            int count = in.nextInt();
            Main.atm.addCash(denomination, count);
            System.out.println("Added " + count + " bills of denomination " + denomination);
            System.out.println("Would you like to restock again?");
            boolean repeat = in.nextBoolean();
            if (!repeat) break;
        }
        return "BankManagerOptions";
    }
}
