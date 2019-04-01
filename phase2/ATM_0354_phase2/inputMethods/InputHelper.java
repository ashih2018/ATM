package ATM_0354_phase2.inputMethods;


import ATM_0354_phase2.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains methods to verify various input types
 */
class VerifyInputs {
    static double verifyDouble(Scanner in) {
        try {
            return Double.parseDouble(in.nextLine());
        } catch (ClassCastException | NumberFormatException e) {
            System.out.println("Invalid amount, please try again.");
            System.out.print(">");
            return verifyDouble(in);
        }
    }

    static int verifyDenomination(Scanner in) {
        try {
            int denomination = Integer.parseInt(in.nextLine());
            if (!new HashSet<>(Arrays.asList(5, 10, 20, 50)).contains(denomination)) {
                System.out.println("That is not a valid denomination. \n Please enter a new denomination.");
                System.out.print(">");
                return verifyDenomination(in);
            } else {
                return denomination;
            }
        } catch (ClassCastException e) {
            System.out.println("Invalid denomination.");
            System.out.println("What denomination would you like to restock?");
            System.out.print(">");
            return verifyDenomination(in);
        }
    }

    static int verifyInt(Scanner in, boolean positive) {
        try {
            if (positive) {
                int input = Integer.parseInt(in.nextLine());
                if (input > 0) {
                    return input;
                } else {
                    System.out.println("Invalid number, please try again");
                    System.out.print(">");
                    return verifyInt(in, true);
                }
            }
            else {
                int input = Integer.parseInt(in.nextLine());
                if (input >= 0) {
                    return input;
                } else {
                    System.out.println("Invalid number, please try again");
                    System.out.print(">");
                    return verifyInt(in, false);
                }
            }
        } catch (ClassCastException | NumberFormatException e) {
            System.out.println("Invalid number, please try again");
            System.out.print(">");
            return verifyInt(in, positive);
        }
    }

    static String verifyNewUsername(Scanner in) {
        String username = in.nextLine();
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");

        if (p.matcher(username).matches() && !Main.atm.usernameExists(username)) {
            return username;
        } else if (!p.matcher(username).matches()) {
            System.out.println("Invalid character in username. Please only use alphanumeric characters (A-Z, a-z, 0-9)\n Please enter a different username");
            System.out.print(">");
            return verifyNewUsername(in);
        } else {
            System.out.println("That username already exists or is invalid. \n Please enter a different username.");
            System.out.print(">");
            return (verifyNewUsername(in));
        }

    }

    static int verifyAccountId(Scanner in, User curUser, String action) {
        int id = verifyInt(in, false);
        try {
            if (curUser.verifyID(id)) return id;
            else {
                System.out.println("Invalid ID, please try again.");
                System.out.println("Which account(id) would you like to " + action + "?");
                System.out.print(">");
                return verifyAccountId(in, curUser, action);
            }
        } catch (ClassCastException e) {
            System.out.println("Invalid id");
            System.out.println("Which account(id) would you like to " + action + "?");
            System.out.print(">");
            return verifyAccountId(in, curUser, action);
        }
    }

    static String verifyNewPassword(Scanner in) {
        String password = in.nextLine();
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");
        if (p.matcher(password).matches()) {
            return password;
        } else {
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
    static Person verifyPerson(Scanner in) {
        String input = in.nextLine();
        if (Main.atm.usernameExists(input))
            return Main.atm.getUser(input);
        System.out.println("Not a valid username.");
        System.out.print(">");
        return verifyPerson(in);
    }

    static String verifyAccountType(Scanner in) {
        String input = in.nextLine().toLowerCase();
        if (new HashSet<>(Arrays.asList("credit card", "line of credit", "chequing", "savings")).contains(input)) {
            return input;
        }
        System.out.println("Invalid account type.");
        System.out.print(">");
        return verifyAccountType(in);
    }

    static String verifyEmailFormat(Scanner in) {
        String input = in.nextLine();
        //Taken from emailregex.com
        Pattern pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        Matcher m = pattern.matcher(input);
        if (m.matches()) {
            return input;
        } else {
            System.out.println("Invalid email, please try again.");
            System.out.print(">");
            return verifyEmailFormat(in);
        }
    }


    static boolean verifyConfirmation(Scanner in) {
        String input = in.nextLine().toLowerCase();
        if (new HashSet<>(Arrays.asList("yes", "y")).contains(input)) {
            return true;
        } else if (new HashSet<>(Arrays.asList("no", "n")).contains(input)) {
            return false;
        }
        else{
            System.out.println("Invalid response.");
            System.out.print(">");
        }

        return verifyConfirmation(in);
    }

    static String verifyStockSymbol(Scanner in) {
        String input = in.nextLine().toUpperCase();
        if (new HashSet<>(Arrays.asList("PSXP", "SOI", "BERY", "SAVE", "MTOR",
                "MTZ", "ISRG", "EPRT", "MIDD", "LPLA", "FAST", "NMIH", "DIN", "MTCH", "REXR", "AMT",
                "BAH", "TDG", "TGE", "AMZN", "XLNX", "KEYS", "ALXN", "V", "WLTW", "AWI",
                "EDU", "PRAH", "LULU", "INTU", "PYPL", "WWD", "STOR", "MGP", "ESNT",
                "CTRE", "FIVE", "PAGS", "FTNT", "ATHM", "PAYC", "ANET", "ULTA", "PLNT",
                "SSNC", "PANW", "VEEV", "UBNT", "KL", "TEAM", "KO", "AAPL", "MSFT")).contains(input)) {
            return input;
        }
        System.out.println("Invalid symbol.");
        System.out.print(">");
        return verifyStockSymbol(in);
    }

    static BigDecimal verifyMoney(Scanner in) {
        BigDecimal amount = BigDecimal.valueOf(VerifyInputs.verifyDouble(in));
        if (amount.signum() == -1 || amount.intValue() == 0) {
            System.out.println("Invalid amount.");
            System.out.print(">");
            return verifyMoney(in);
        }
        return amount;

    }
}

class HelperFunctions {
    static void transferToOtherUser(int fromId, Scanner in) {
        System.out.println("What is the username of the user you would like to transfer to?");
        User otherUser = VerifyInputs.verifyUser(in);
        System.out.println("How much money would you like to transfer?");
        System.out.print(">");
        BigDecimal amount = VerifyInputs.verifyMoney(in);
        Account fromAccount = ((User) Main.atm.getCurUser()).getAccount(fromId);
        Account toAccount = otherUser.getPrimaryAccount();
        Transaction transfer = new Transfer(fromAccount, toAccount, amount);
        transfer.process();
    }

    static void transferToOwnAccount(int fromId, Scanner in) {
        System.out.println("Which account would you like to transfer to?");
        int id;
        User curUser = (User) Main.atm.getCurUser();
        while (true) {
            id = VerifyInputs.verifyAccountId(in, curUser, "transfer to");
            if (curUser.getAccount(id).canTransferIn()) {
                break;
            } else {
                System.out.println("You can't transfer to that account. Please try again");
                System.out.print(">");
            }
        }
        System.out.println("How much money would you like to transfer?");
        System.out.print(">");
        BigDecimal amount = VerifyInputs.verifyMoney(in);
        Account fromAccount = ((User) Main.atm.getCurUser()).getAccount(fromId), toAccount = ((User) Main.atm.getCurUser()).getAccount(id);
        Transaction transfer = new Transfer(fromAccount, toAccount, amount);
        transfer.process();
    }
}