package ATM_0354;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    static ATM atm;

    public static void main(String[] args) {
        atm = new ATM();
        atm.setDateTime(LocalDateTime.now());
        Scanner in = new Scanner(System.in); //Set up for console input right now

        //example input from console
        System.out.println("What is your name?");
        String name = in.nextLine();
        System.out.println("Your name is: " + name);
    }
}
