package ATM_0354.inputMethods;

import ATM_0354.InputMethod;
import ATM_0354.Main;
import ATM_0354.User;

import java.math.BigDecimal;
import java.util.Scanner;

public class WithdrawMoneyMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.println("======= Withdraw Money =======");
        while(true) {
            System.out.println("==== Your Accounts ====");
            String accountSummary = ((User) Main.atm.getCurUser()).getSummary();
            System.out.println(accountSummary.equals("") ? "No accounts" : accountSummary); //I love ternary operators
            if (accountSummary.equals("")) {
                System.out.println("Input anything to go back.");
                System.out.print(">");
                in.nextLine();
                return "UserOptions";
            } else {
                int id = askForID(in);
                BigDecimal money = askForMoney(in);
                Main.atm.withdraw((User) Main.atm.getCurUser(), money, id);
                System.out.println("Would you like to withdraw more money?");
                System.out.print(">");
                boolean cont = in.nextLine().equals("yes");
                if(!cont){
                    return "UserOptions";
                }
            }
        }
    }
    private int askForID(Scanner in){
        System.out.println("Which account (id) would you like to withdraw from?");
        System.out.print(">");
        int id = -1;
        try {
            id = in.nextInt();
        } catch(ClassCastException e){
            System.out.println("Invalid id");
            return askForID(in);
        }
        //TODO: check if id is within an acceptable range
        return id;
    }
    private BigDecimal askForMoney(Scanner in){
        System.out.println("How much money would you like to withdraw?");
        System.out.print(">");
        double money = -1;
        try{
            money = in.nextDouble();
        } catch(ClassCastException e){
            System.out.println("Invalid amount");
            return askForMoney(in);
        }
        return BigDecimal.valueOf(money);
    }
}
