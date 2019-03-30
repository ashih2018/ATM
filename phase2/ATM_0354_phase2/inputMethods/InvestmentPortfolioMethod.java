package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.User;

import java.util.Scanner;

public class InvestmentPortfolioMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        if(Main.atm.getCurUser() instanceof User){
            User user = (User) Main.atm.getCurUser();
            System.out.println("======= Investment Portfolio =======");
            if(! user.hasInvestmentAccount()){
                System.out.println("You do not have an investment account. Would you like to request one? (Y/N)");
                if(VerifyInputs.verifyConfirmation(in)){
                    Main.atm.requestCurAccount("investment");
                    System.out.println("Investment account requested successfully.\n" +
                            "Input anything to go back.");
                    System.out.println(">");
                    in.nextLine();
                    return "UserOptions";
                }
                return "UserOptions";
            }
            else{
                System.out.println(user.getInvestmentPortfolio());
                System.out.println("Would you like to buy/sell stocks? (Y/N)");
                if(VerifyInputs.verifyConfirmation(in)){
                    tradeStocks();
                }
                else{
                    System.out.println("Input anything to go back.");
                    System.out.println(">");
                    in.nextLine();
                    return "UserOptions";
                }
            }
        }
        return "UserOptions";
    }

    private void tradeStocks(){

    }
}
