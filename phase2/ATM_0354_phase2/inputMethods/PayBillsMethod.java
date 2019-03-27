package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;

import java.math.BigDecimal;
import java.util.Scanner;

public class PayBillsMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("Who would you like to pay a bill to?\n>");
        String destination = in.nextLine();

        System.out.println("Which account will you use to pay?\n>");
        String accType;
        while(true){
            accType= in.nextLine();
            break;
        }
        System.out.println("How much will you pay?\n>");
        BigDecimal amount;
        while(true){
            try{
                amount = BigDecimal.valueOf(Double.parseDouble(in.nextLine()));
                break;
            }
            catch(ClassCastException c){
                System.out.println("Invalid amount, please try again.\n>");
            }
        }

        return "UserOptions";
    }
}
