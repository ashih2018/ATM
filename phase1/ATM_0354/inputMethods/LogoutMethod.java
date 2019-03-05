package ATM_0354.inputMethods;

import ATM_0354.InputMethod;
import ATM_0354.Main;

import java.util.Scanner;

public class LogoutMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        Main.atm.signOut();
        System.out.println("Logging out");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Login";
    }
}
