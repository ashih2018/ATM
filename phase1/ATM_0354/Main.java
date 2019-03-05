package ATM_0354;

import jdk.internal.util.xml.impl.Input;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static ATM atm;

    private static InputHandler ih;
    private static String state;
    public static void main(String[] args) {
        atm = new ATM();
        ih = new InputHandler();
        //need to set up current date - as previous date + 1 day

        //TODO: check if this is the first time we've started the ATM; if so, set up local date, bankmanager, intial funds, etc.
        boolean firstTime = true;
        if(firstTime) {
            atm.setDateTime(LocalDateTime.now());
            state = "setUpBankManager";
        }
        else{
            state = "login";
        }
        Scanner in = new Scanner(System.in); //Set up for console input right now

//        //example input from console
//        System.out.println("What is your name?");
//        String name = in.nextLine();
//        System.out.println("Your name is: " + name);
        generateUI(in);
    }

    private static void generateUI(Scanner in){

        while(true) {
            state = ih.handleInput(state);
        }
    }
}
