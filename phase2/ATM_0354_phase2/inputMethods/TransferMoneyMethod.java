package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.Account;
import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TransferMoneyMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
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
                int id = -1;
                while(true) {
                    try {
                        boolean flag;
                        do {
                            System.out.print(">");
                            try
                            {
                                id = in.nextInt();
                                flag=true;
                            }
                            catch (InputMismatchException exception)
                            {
                                System.out.println("Not a numerical value.");
                                in.nextLine();
                                flag = false;
                            }
                        } while(!flag);
                        boolean idExists = ((User) Main.atm.getCurUser()).verifyID(id);
                        boolean canTransferOut = false;
                        Account userAccount = ((User) Main.atm.getCurUser()).getAccount(id);
                        if (userAccount != null) {
                            canTransferOut = (((User) Main.atm.getCurUser()).getAccount(id).canTransferOut());
                        }
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
                System.out.println("Press \'A\' for your own account. \nPress \'B\' for a different user's account.");
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
                            HelperFunctions.transferToOwnAccount(id, in);
                            break;
                        }
                        else if(letter.substring(0, 1).equalsIgnoreCase("b")){
                            HelperFunctions.transferToOtherUser(id, in);
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

}
