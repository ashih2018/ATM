package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.Parser;
import ATM_0354_phase2.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class DepositMoneyMethod implements InputMethod {

    private static final String DEPOSIT_FILE_NAME = "phase2/ATM_0354_phase2/Files/deposits.txt";

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
                User curUser = (User) Main.atm.getCurUser();
                int id = VerifyInputs.verifyAccountId(in, curUser, "deposit into");
                Parser parser = new Parser();
                List<String> all_deposits = parser.parseDeposits();
                displayDeposits(all_deposits);
                if (all_deposits.size() == 0) {
                    System.out.println("No deposits available.");
                    System.out.println("Returning to user options...");
                    updateDeposits(all_deposits);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "UserOptions";
                }

                System.out.println("Select a deposit by inputting 'deposit_type, amount': ");
                System.out.println("=================================");
                System.out.print(">");
                String input = in.nextLine();
                while (true) {
                    System.out.println("Input: " + input);
                    if (all_deposits.contains(input)) {
                        all_deposits.remove(input);
                        break;
                    } else {
                        System.out.println("Invalid deposit.");
                        System.out.println("Select a deposit by inputting 'deposit_type, amount': ");
                        System.out.print(">");
                        input = in.nextLine();
                    }
                }

                String[] items = input.split(", "); // what about cheque/cash (i.e. items[0])?
                BigDecimal money = new BigDecimal(items[1]);
                Main.atm.deposit((User) Main.atm.getCurUser(), money, id);
                updateDeposits(all_deposits);
                displayDeposits(all_deposits);
                if (all_deposits.size() == 0) {
                    System.out.println("Returning to user options...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "UserOptions";
                }
                System.out.println("Would you like to deposit more money? (yes/no)");
                System.out.print(">");
                if (!VerifyInputs.verifyConfirmation(in)) return "UserOptions";
            }
        }
    }

    private void displayDeposits(List<String> deposits) {
        System.out.println("==== Available Deposits ====");
        if (deposits.size() == 0)
            System.out.println("No available deposits.");
        else {
            for (String deposit : deposits) {
                System.out.println(deposit);
            }
        }
    }

    private void updateDeposits(List<String> deposits) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(DEPOSIT_FILE_NAME), false));
            for (String deposit : deposits) {
                writer.write(deposit);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println("IOException when updating deposit in deposits.txt");
        }
    }

}
