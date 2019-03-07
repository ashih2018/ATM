package ATM_0354;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static ATM atm;

    private static InputHandler ih;
    private static String state;
    private static final String PEOPLEFILENAME = "";
    public static void main(String[] args) throws IOException{
        atm = new ATM();
        ih = new InputHandler();

        Scanner file_in = new Scanner(new File(PEOPLEFILENAME));
        boolean firstTime = file_in.hasNext();
        if(firstTime) {
            atm.setDateTime(LocalDateTime.now());
            state = "SetUpBankManager";
        }
        else{
            //TODO: Set up local date, manager, users, funds, etc.
            state = "Login";
        }
        Scanner in = new Scanner(System.in); //Set up for console input right now
        generateUI(in);
    }

    private static void generateUI(Scanner in){
        while(!state.equals("Exit")) {
            state = ih.handleInput(state, in);
            clearScreen(); //TODO: see if there's a more elegant way to do this
        }
        //TODO: Add code that writes to files, etc on exit
    }
    private static void clearScreen(){
        for(int i=0; i<50; i++) {
            System.out.println();
        }
    }

    //TODO: Check two methods below.
    private static final String DEPOSITFILENAME = "phase1/ATM_0354/Files/deposits.txt";

    // public static BigDecimal parseDeposits()
    public static List<String> parseDeposits(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(DEPOSITFILENAME));

            String sCurrentLine;
            String[] items;
            String deposit_type;
            BigDecimal deposit_amount;

            List<String> all_deposits = new ArrayList<>();
            while ((sCurrentLine = br.readLine()) != null) {
                items = sCurrentLine.split(",");
                deposit_type = items[0];
                deposit_amount = new BigDecimal(items[1]);
                all_deposits.add(deposit_type + "," + deposit_amount);
            }
            return all_deposits;

        } catch (Exception e) {
            System.out.println("No Deposits.");
            return null;
        }
    }

    public void outgoingHandlerThingy() {
        try {
            String outgoingPath = "phase1/ATM_0354/Files/outgoing.txt";
            BufferedWriter br = new BufferedWriter(new FileWriter(outgoingPath, true));
            //todo: find appropriate class and replace str with vars.
            br.write("bill_name" + "," + "user" + "," + 555.55 + "," + "some LocalDateTime" + "\n");

            br.close();
        } catch (Exception e) {
            System.out.println("File does not exist.");
        }
    }
}
