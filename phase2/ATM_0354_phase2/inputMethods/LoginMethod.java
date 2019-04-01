package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.*;

import java.util.Scanner;

public class LoginMethod implements InputMethod {

    public String run(Scanner in){
        System.out.println("========== Login ==========");
        System.out.println("Type 'Forgot Password' to reset your password.");
        System.out.print("Username: ");
        String username = in.nextLine();
        if (username.toLowerCase().equals("forgot password")) {
            System.out.println("What's your username?");
            System.out.print(">");
            Person curPerson = VerifyInputs.verifyPerson(in);
            if(curPerson.hasEmail()){
                System.out.println(String.format("Sending a reset password email to your saved email (%s)....", curPerson.getEmail()));
                int securityNum = EmailHandler.resetPassword(curPerson, curPerson.getEmail());
                if (securityNum == -1) {
                    System.out.println("Unable to send email.");
                }
                else {
                    System.out.println("We have sent you a security number! Please enter it in below.");
                    System.out.print(">");
                    int userNum = VerifyInputs.verifyInt(in, false);
                    if (securityNum == userNum) {
                        System.out.println("Enter your new password");
                        System.out.print(">");
                        String password =VerifyInputs.verifyNewPassword(in);
                        System.out.println("Your new password is: " + password);
                        curPerson.setPassword(password);
                        return "UserOptions";
                    }
                    else {
                        System.out.println("Incorrect Security Number;");
                        return "Login";
                    }
                }
            }
            else{
                System.out.println("This user doesn't have any emails associated with them. They can't reset their password unfortunately.");
                System.out.print(">");
                in.nextLine();
                return "Login";
            }

        }
        System.out.print("Password: ");
        String password = in.nextLine();
        Person curUser = Main.atm.signIn(username, password);
        if(curUser != null){
            if(curUser instanceof BankManager){
                return "BankManagerOptions";
            }
            else{
                return "UserOptions";
            }
        }
        else{
            System.out.println("Incorrect Login, try again.");
            return this.run(in);
        }
    }

}
