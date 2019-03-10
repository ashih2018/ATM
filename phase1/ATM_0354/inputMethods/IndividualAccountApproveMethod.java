package ATM_0354.inputMethods;

import ATM_0354.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class IndividualAccountApproveMethod implements InputMethod {
    @Override
    public String run(Scanner in) {

        String filePath = "phase1/ATM_0354/Files/account_creation_requests.txt";
        try{
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while(!line.equals("")){
                String[] strings = line.split(",");
                String username = strings[0];
                String accountType = strings[1];
                String date = strings[2];
                System.out.println("Approve a \"" + accountType + "\" Account for \"" + username + "\"?");
                System.out.println("Press \'yes\'/\'no\'");
                boolean approved;
                while (true){
                    try{
                        approved = in.nextLine().equalsIgnoreCase("yes");
                        break;
                    }
                    catch(ClassCastException e){
                        System.out.println("Approve a \"" + accountType + "\" Account for \"" + username + "\"?");
                        System.out.println("Press \'yes\'/\'no\'");
                    }
                }
                if (approved){
                    ((User)Main.atm.getUser(username)).addAccount(accountType.toUpperCase().
                            replaceAll("\\s+","") + "ACCOUNT");
                }
                line = reader.readLine();
            }
        }
        catch(IOException e){
            System.out.println(e.toString());
            System.out.println("IOException caught when reading file in IndividualAccountApproveMethod.");
        }
        return "BankManagerOptions";
    }
}
