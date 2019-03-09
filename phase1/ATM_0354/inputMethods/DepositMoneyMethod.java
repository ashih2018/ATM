package ATM_0354.inputMethods;

import ATM_0354.InputMethod;
import ATM_0354.Main;
import ATM_0354.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class DepositMoneyMethod implements InputMethod {

    @Override
    public String run(Scanner in) {
        System.out.println("======= Deposit Money =======");
        while (true) {
            System.out.println("==== Your Accounts ====");
            String accountSummary = Main.atm.viewCurAccounts();
            System.out.println(accountSummary.equals("") ? "No accounts" : accountSummary);
            if (accountSummary.equals("")) {
                System.out.println("Input anything to go back.");
                System.out.print(">");
                in.nextLine();
                return "UserOptions";
            } else {
                System.out.println("Which account (id) would you like to deposit into?");
                System.out.print(">");
                int id;
                while(true) {
                    try {
                        id = in.nextInt();
                        break;
                    } catch (ClassCastException e) {
                        System.out.println("Invalid id");
                        System.out.println("Which account (id) would you like to deposit into?");
                        System.out.print(">");
                    }
                }

                System.out.println("Depositing from deposits.txt....");
                //TODO: Figure out what's happening here

                List<String> all_deposits = Main.parseDeposits();
                displayDeposits(all_deposits);

                System.out.println("Select a deposit by inputting 'deposit_type, amount': ");
                String input = "replace this";
                
                // if all_deposits.contains(input)
                all_deposits.remove(input);

                String[] items = input.split(", "); // what about cheque/cash (i.e. items[0])?
                BigDecimal money = new BigDecimal(items[1]);
                Main.atm.deposit((User) Main.atm.getCurUser(), money, id);

                System.out.println("Would you like to deposit more money?");
                System.out.print(">");
                boolean cont = in.nextLine().equals("yes");
                displayDeposits(all_deposits); //todo is this a good place?
                if (!cont) {
                    return "UserOptions";
                }
            }
        }
    }

    public void displayDeposits(List<String> deposits){
        System.out.println("==== Available Deposits ====");
        if (deposits.size() == 0)
            System.out.println("No available deposits.");
        else{
            for (String deposit: deposits) {
                System.out.println(deposit);
            }
        }
    }

}
