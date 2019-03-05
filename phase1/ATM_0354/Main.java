package ATM_0354;

import java.math.BigDecimal;
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
            state = "SetUpBankManager";
        }
        else{
            state = "Login";
        }
        Scanner in = new Scanner(System.in); //Set up for console input right now
        generateUI(in);
    }

    private static void generateUI(Scanner in){
        while(true) { //Note that the program will wait for scanner input, so this shouldn't infinite loop / crash the program
            state = ih.handleInput(state, in);
            clearScreen(); //TODO: see if there's a more elegant way to do this
        }
    }
    private static void clearScreen(){
        for(int i=0; i<50; i++) {
            System.out.println();
        }
    }
    //TODO: Complete
    public static BigDecimal parseDeposits(){
        return null;
    }
}
