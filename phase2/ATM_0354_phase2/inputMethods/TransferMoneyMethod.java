package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.MoneyTransferException;
import ATM_0354_phase2.User;

import java.math.BigDecimal;
import java.util.Scanner;

public class TransferMoneyMethod implements InputMethod {
    Scanner in;
    @Override
    public String run(Scanner in) {
        this.in = in;
        System.out.println("======= Transfer Money =======");
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
                System.out.println("Which account (id) would you like to transfer from?");
                System.out.print(">");
                int id;
                while(true) {
                    try {
                        id = in.nextInt();
                        boolean idExists = ((User) Main.atm.getCurUser()).verifyID(id);
                        boolean canTransferOut = (((User) Main.atm.getCurUser()).getAccount(id).canTransferOut());
                        if (idExists && canTransferOut)
                            break;
                        else{
                            System.out.println("Invalid id");
                            System.out.println("Which account (id) would you like to transfer from?");
                        }
                    } catch (ClassCastException e) {
                        System.out.println("Invalid id");
                        System.out.println("Which account (id) would you like to transfer from?");
                        System.out.print(">");
                    }
                }

                System.out.println("Would you like to transfer to your own account or a different user's account?");
                System.out.println("Press \'A\' for your own account. \n Press \'B\' for a different user's account.");
                while(true) {
                    boolean valid = false;
                    String letter = "";
                    try {
                        letter = in.nextLine();
                        valid = letter.length() > 0;
                    }catch (ClassCastException e) {
                        System.out.println("Please enter \'A\' for your own account or \'B\' for a different " +
                                "user's account.");
                        System.out.print(">");
                    }
                    if (valid){
                        if(letter.substring(0, 1).equalsIgnoreCase("a")) {
                            transferToOwnAccount(id);
                            break;
                        }
                        else if(letter.substring(0, 1).equalsIgnoreCase("b")){
                            transferToOtherUser(id);
                            break;
                        }
                        else{
                            System.out.println("Please enter \'A\' for your own account or \'B\' for a different " +
                                    "user's account.");
                            System.out.println(">");
                        }
                    }

                }
                System.out.println("Would you like to transfer more money?");
                System.out.print(">");
                boolean cont = in.nextLine().equals("yes");
                if (!cont) {
                    return "UserOptions";
                }
            }
        }
    }
    private void transferToOwnAccount(int fromId){
        System.out.println("Which account would you like to transfer to?");
        System.out.print(">");
        int id;
        while(true) {
            try {
                id = in.nextInt();
                boolean idExists = ((User) Main.atm.getCurUser()).verifyID(id);
                boolean canTransferIn = ((User) Main.atm.getCurUser()).getAccount(id).canTransferIn();
                if (idExists && canTransferIn)
                    break;
                else{
                    System.out.println("That id is invalid.");
                    System.out.println("Which account (id) would you like to transfer to?");
                    System.out.println(">");
                }
            } catch (ClassCastException e) {
                System.out.println("Invalid id");
                System.out.println("Which account (id) would you like to transfer to?");
                System.out.print(">");
            }
        }
        BigDecimal amount = askForMoney();
        try{
            ((User) Main.atm.getCurUser()).getAccount(fromId).transferMoneyOut(amount);
            ((User) Main.atm.getCurUser()).getAccount(id).transferMoneyIn(amount);
        }
        catch(MoneyTransferException e){
            System.out.println("Money transfer exception when transferring between own accounts. \n" +
                    "I thought I prevented this though, so that's bad.");
        }
    }

    public void transferToOtherUser(int fromId){
        System.out.println("What is the username of the user you would like to transfer to?");
        String username;
        while (true){
            try{
                username = in.nextLine();
                boolean usernameExists = Main.atm.usernameExists(username);
                if(usernameExists)
                    break;
                else{
                    System.out.println("Username does not exist.");
                    System.out.println("What is the username of the user you would like to transfer to?");
                    System.out.println(">");
                }
            } catch (ClassCastException e){
                System.out.println("Invalid username.");
                System.out.println("What is the username of the user you would like to transfer to?");
                System.out.println(">");
            }
        }
        BigDecimal amount = askForMoney();
        try{
            ((User) Main.atm.getCurUser()).getAccount(fromId).transferMoneyOut(amount);
            ((User) Main.atm.getUser(username)).defaultTransferIn(amount);
        }
        catch(MoneyTransferException e){
            System.out.println("Money transfer exception when transferring between users. \n" +
                    "I thought I prevented this though, so that's bad.");
        }
    }

    private BigDecimal askForMoney(){
        System.out.println("How much money would you like to transfer?");
        System.out.println(">");
        BigDecimal amount;
        while (true){
            try{
                amount = in.nextBigDecimal();
                return amount;
            }
            catch(ClassCastException e) {
                System.out.println("Invalid amount");
                System.out.println("How much money would you like to transfer?");
                System.out.println(">");
            }
        }
    }
}
