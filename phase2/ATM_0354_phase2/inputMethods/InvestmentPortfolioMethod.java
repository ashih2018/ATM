package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.User;

import java.util.Scanner;

public class InvestmentPortfolioMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        if(Main.atm.getCurUser() instanceof User){
            User curUser = (User) Main.atm.getCurUser();
            System.out.println("======= Investment Portfolio =======");
            if(!curUser.hasInvestmentAccount()){
                System.out.println("You do not have an investment account. Would you like to request one? (Y/N)");
                if(VerifyInputs.verifyConfirmation(in)){
                    Main.atm.requestAccount(curUser, "investment");
                    System.out.println("Investment account requested successfully.\n" +
                            "Input anything to go back.");
                    System.out.println(">");
                    in.nextLine();
                    return "UserOptions";
                }
                return "UserOptions";
            }
            else{
                System.out.println(curUser.getInvestmentPortfolio());
                System.out.println("Would you like to trade stocks? (Y/N)");
                if(VerifyInputs.verifyConfirmation(in)){
                    tradeStocks(in);
                    while(true){
                        System.out.println("Would you like to trade more stocks? (Y/N)");
                        if(VerifyInputs.verifyConfirmation(in)){
                            tradeStocks(in);
                        }
                        else{
                            return "UserOptions";
                        }
                    }

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

    private void tradeStocks(Scanner in){
        System.out.println("Would you like to buy stocks? (Y/N)");
        if(VerifyInputs.verifyConfirmation(in)){
            buyStocks(in);
        }
        else{
            System.out.println("Would you like to sell stocks? (Y/N)");
            if(VerifyInputs.verifyConfirmation(in)){
                sellStocks(in);
            }
        }
    }

    private void buyStocks(Scanner in){
        System.out.println("What stock would you like to buy?\n" +
                "Enter the stock symbol.");
        System.out.print(">");
        String symbol = VerifyInputs.verifyStockSymbol(in);
        System.out.println("How many stocks would you like to buy?");
        System.out.print(">");
        int quantity = VerifyInputs.verifyInt(in, true);
        ((User) Main.atm.getCurUser()).buyStock(symbol, quantity);
    }

    private void sellStocks(Scanner in){
        System.out.println("What stock would you like to sell?\n" +
                "Enter the stock symbol.");
        System.out.print(">");
        String symbol = VerifyInputs.verifyStockSymbol(in);
        System.out.println("How many stocks would you like to sell?");
        System.out.print(">");
        int quantity = in.nextInt();
        ((User) Main.atm.getCurUser()).sellStock(symbol, quantity);
    }
}
