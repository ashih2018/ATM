package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.*;

import java.util.Scanner;

public class LoginMethod implements InputMethod {
    private EmailHandler emailHandler = new EmailHandler();
    public String run(Scanner in){
        System.out.println("========== Login ==========");
        System.out.println("Type 'Forgot Password' to reset your password.");
        System.out.print("Username: ");
        String username = in.nextLine();
        if (username.toLowerCase().equals("forgot password")) {
            System.out.print("Enter email: ");
            String email = in.nextLine();
            int securityNum = this.emailHandler.resetPassword(((User)Main.atm.getCurUser()), email);
            if (securityNum == -1) {
                System.out.println("Unable to send email.");
            }
            else {
                System.out.println("We have sent you a security number! Please enter it in below.");
                System.out.print(">");
                int userNum = VerifyInputs.verifyInt(in);
                if (securityNum == userNum) {
                    System.out.println("Enter your new password");
                    System.out.print(">");
                    String password =VerifyInputs.verifyNewPassword(in);
                    System.out.println("Your new password is: " + password);
                    (Main.atm.getCurUser()).setPassword(password);
                }
                else {
                    System.out.println("Incorrect Security Number;");
                    return "UserOptions";
                }
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
