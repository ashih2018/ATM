package ATM_0354_phase2.inputMethods;


import ATM_0354_phase2.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains methods to verify various input types
 */
class VerifyInputs {
    static double verifyDouble(Scanner in){
        try{
            return in.nextDouble();
        } catch(ClassCastException e){
            System.out.println("Invalid amount, please try again.");
            System.out.print(">");
            return verifyDouble(in);
        }
    }
    static int verifyInt(Scanner in){
        try{

            int input = Integer.parseInt(in.nextLine());
            if(input >= 0) {
                return input;
            }
            else {
                System.out.println("Invalid number, please try again");
                System.out.print(">");
                return verifyInt(in);
            }

        }catch (ClassCastException | NumberFormatException e){
            System.out.println("Invalid number, please try again");
            System.out.print(">");
            return verifyInt(in);
        }
    }
    static String verifyNewUsername(Scanner in){
        String username = in.nextLine();
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");

        if(p.matcher(username).matches() &&!Main.atm.usernameExists(username)){
            return username;
        }
        else if(!p.matcher(username).matches()) {
            System.out.println("Invalid character in username. Please only use alphanumeric characters (A-Z, a-z, 0-9)\n Please enter a different username");
            System.out.print(">");
            return verifyNewUsername(in);
        }
        else {
            System.out.println("That username already exists or is invalid. \n Please enter a different username.");
            System.out.print(">");
            return(verifyNewUsername(in));
        }

    }
    static int verifyAccountId(Scanner in, User curUser, String action){

        int id = verifyInt(in);
        try{
            if(curUser.verifyID(id)) return id;
            else return verifyAccountId(in, curUser, action);
        } catch (ClassCastException e){
            System.out.println("Invalid id");
            System.out.println("Which account(id) would you like to "+action +"?");
            System.out.print(">");
            return verifyAccountId(in, curUser, action);
        }
    }
    static String verifyNewPassword(Scanner in){
        String password = in.nextLine();
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");
        if(p.matcher(password).matches()){
            return password;
        }
        else{
            System.out.println("Invalid character in password. Please only use alphanumeric characters (A-Z, a-z, 0-9)\n Please enter a different username");
            System.out.print(">");
            return verifyNewPassword(in);
        }
    }
    static User verifyUser(Scanner in) {
        String input = in.nextLine();
        if (Main.atm.usernameExists(input) && Main.atm.getUser(input) instanceof User)
            return (User) Main.atm.getUser(input);

        System.out.println("Not a valid username.");
        System.out.print(">");
        return verifyUser(in);
    }
    static String verifyAccountType(Scanner in) {
        String input = in.nextLine();
        if (new HashSet<>(Arrays.asList("credit card", "line of credit", "chequing", "savings")).contains(input)) {
            return input;
        }
        System.out.println("Invalid account type.");
        System.out.print(">");
        return verifyAccountType(in);
    }
    static String verifyEmailFormat(Scanner in){
        String input = in.nextLine();
        //Taken from emailregex.com
        Pattern pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        Matcher m = pattern.matcher(input);
        if(m.matches()){
            return input;
        }
        else{
            System.out.println("Invalid email, please try again.");
            System.out.print(">");
            return verifyEmailFormat(in);
        }
    }
}
class HelperFunctions {
    static  void transferToOtherUser(int fromId, Scanner in){
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
                    System.out.print(">");
                }
            } catch (ClassCastException e){
                System.out.println("Invalid username.");
                System.out.println("What is the username of the user you would like to transfer to?");
                System.out.print(">");
            }
        }
        System.out.println("How much money would you like to transfer?");
        System.out.print(">");
        BigDecimal amount = BigDecimal.valueOf(VerifyInputs.verifyDouble(in));
        Account fromAccount =((User) Main.atm.getCurUser()).getAccount(fromId);
        Account toAccount = ((User) Main.atm.getUser(username)).getPrimaryAccount();
        Transaction transfer = new Transfer(fromAccount, toAccount, amount);
        transfer.process();
    }
    static void transferToOwnAccount(int fromId, Scanner in){
        System.out.println("Which account would you like to transfer to?");
        int id = -1;
        //TODO: figure this out
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
                    System.out.println("That id is invalid.");
                    System.out.println("Which account (id) would you like to transfer to?");
                    System.out.print(">");
                }
            } catch (ClassCastException e) {
                System.out.println("Invalid id");
                System.out.println("Which account (id) would you like to transfer to?");
                System.out.print(">");
            }
        }
        System.out.println("How much money would you like to transfer?");
        System.out.print(">");
        BigDecimal amount = BigDecimal.valueOf(VerifyInputs.verifyDouble(in));
        Account fromAccount =((User) Main.atm.getCurUser()).getAccount(fromId), toAccount = ((User) Main.atm.getCurUser()).getAccount(id);
        Transaction transfer = new Transfer(fromAccount, toAccount, amount);
        transfer.process();
    }
}