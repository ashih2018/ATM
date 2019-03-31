package ATM_0354_phase2;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ATM atm;

    private static InputHandler ih;
    private static String state;
    private static final String PEOPLE_FILE_NAME = "phase2/ATM_0354_phase2/Files/people.txt";
    private static final String DEPOSIT_FILE_NAME = "phase2/ATM_0354_phase2/Files/deposits.txt";
    private static final String ACCOUNT_REQUESTS_FILE_NAME = "phase2/ATM_0354_phase2/Files/account_creation_requests.txt";
    private static final String ALERTS_FILE_NAME = "phase2/ATM_0354_phase2/Files/alerts.txt";
    private static final String OUTGOING_FILE_NAME = "phase2/ATM_0354_phase2/Files/outgoing.txt";
    private static final String ATM_FILE_NAME = "phase2/ATM_0354_phase2/Files/atm.txt";
    private static final String TRANSACTIONS_FILE = "phase2/ATM_0354_phase2/Files/transactions.txt";
    private static final String STOCKS_FILE_NAME = "phase2/ATM_0354_phase2/Files/stocks.txt";


    public static void main(String[] args) throws IOException {
        atm = new ATM();
        ih = new InputHandler();
        new EmailHandler();
        Scanner fileIn = new Scanner(new File(PEOPLE_FILE_NAME));
        boolean firstTime = !fileIn.hasNext();
        if (firstTime) {
            atm.setDateTime(LocalDateTime.now());
            state = "SetUpBankManager";
            atm.cashHandler.addCash(50, 100);
            atm.cashHandler.addCash(20, 100);
            atm.cashHandler.addCash(10, 100);
            atm.cashHandler.addCash(5, 100);
        } else {
            Parser parser = new Parser();
            Scanner atmFileIn = new Scanner(new File(ATM_FILE_NAME));
            String date = atmFileIn.nextLine();
            LocalDateTime curDate = LocalDateTime.parse(date).plusDays(1);
            atm.newDate(curDate);
            ArrayList<CashObject> cash = new ArrayList<>();
            while (atmFileIn.hasNextLine()) { //update cash amounts
                String[] lineInput = atmFileIn.nextLine().split(",");
                cash.add(new CashObject((Integer.parseInt(lineInput[0])), Integer.parseInt(lineInput[1])));
            }
            atm.cashHandler = new CashHandler(cash);
            parser.parseUsers(fileIn); //Also sets up accounts
            parser.parseTransactions();
            parser.parseStocks();
            state = "Login";
        }

        generateUI(new Scanner(System.in)); //Set up for console input
    }

    private static void generateUI(Scanner in) {
        while (!state.equals("Shutdown") && !state.equals("ShutdownReset")) {
            state = ih.handleInput(state, in);
            clearScreen();
        }
        shutdownATM();
        if (state.equals("ShutdownReset")) {
            reset();
        }
    }

    private static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }


    private static void shutdownATM() {
        Writer writer = new Writer();
        writer.writeATM();
        writer.writePeople();
        writer.writeTransactions();
        writer.writeStocks();
    }

    public static void reset() {
        try {
            String[] paths = {PEOPLE_FILE_NAME, DEPOSIT_FILE_NAME, OUTGOING_FILE_NAME,
                    ATM_FILE_NAME, ALERTS_FILE_NAME, ACCOUNT_REQUESTS_FILE_NAME, TRANSACTIONS_FILE, STOCKS_FILE_NAME};
            BufferedWriter bw;
            for (String path : paths) {
                bw = new BufferedWriter(new FileWriter(new File(path), false));
                bw.write("");
                bw.close();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println("IOException when resetting the program.");
        }
    }

}
