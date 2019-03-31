package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class IndividualAccountApproveMethod implements InputMethod {
    @Override
    public String run(Scanner in) {

        String filePath = "phase2/ATM_0354_phase2/Files/account_creation_requests.txt";
        try {
            Writer writer = new Writer();
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null && !line.equals("")) {
                String[] strings = line.split(",");
                String user1 ="",user2="", accountType="", date="";
                switch (strings[0]) {
                    case "Joint": {
                        user1 = strings[1];
                        user2 = strings[2];
                        accountType = strings[3];
                        date = strings[4];
                        break;
                    }
                    case "Individual": {
                        user1 = strings[1];
                        accountType = strings[2];
                        date = strings[3];
                        break;
                    }
                    default:
                        System.out.println("Error in reading account creation requests.");
                        break;
                }
                boolean approved;
                if(strings[0].equals("Joint")){
                    System.out.println(String.format("(%s) Approve a '%s' account for %s and %s? (yes/no)", date, accountType, user1, user2));
                    System.out.print(">");
                    approved = VerifyInputs.verifyConfirmation(in);
                }
                else {
                    System.out.println(String.format("(%s) Approve a '%s' account for %s? (yes/no)", date, accountType, user1));
                    System.out.print(">");
                    approved = VerifyInputs.verifyConfirmation(in);
                }
                if (approved) {
                    if(strings[0].equals("Joint")) {
                        String accType = accountType.toUpperCase().replaceAll("\\s+", "")+"ACCOUNT";
                        int id = ((User) Main.atm.getUser(user1)).addAccount(accType);
                        ((User) Main.atm.getUser(user2)).addAccount(((User)(Main.atm.getUser(user1))).getAccount(id));
                    }
                    else{
                        String accType = accountType.toUpperCase().replaceAll("\\s+", "")+"ACCOUNT";
                        ((User) Main.atm.getUser(user1)).addAccount(accType);
                    }
                }
                line = reader.readLine();
            }
            writer.overwriteRequests();
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println("IOException caught when reading file in IndividualAccountApproveMethod.");
        }
        if(Main.atm.getCurUser() instanceof BankManager)
            return "BankManagerOptions";
        else
            return "BankEmployeeOptions";
    }
}
