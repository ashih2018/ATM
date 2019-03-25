package ATM_0354_phase1;

import ATM_0354_phase2.ATM;
import ATM_0354_phase2.BankDaddy;
import ATM_0354_phase2.Bill;
import ATM_0354_phase2.CashHandler;
import ATM_0354_phase2.CashObject;
import ATM_0354_phase2.InputHandler;
import ATM_0354_phase2.Person;
import ATM_0354_phase2.Transaction;
import ATM_0354_phase2.User;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static ATM_0354_phase2.ATM atm;

    private static ATM_0354_phase2.InputHandler ih;
    private static String state;
    private static final String PEOPLE_FILE_NAME = "phase1/ATM_0354_phase1/ATM_0354_phase2.Files/people.txt";
    private static final String DEPOSIT_FILE_NAME = "phase1/ATM_0354_phase1/ATM_0354_phase2.Files/deposits.txt";
    private static final String ACCOUNT_REQUESTS_FILE_NAME = "phase1/ATM_0354_phase1/ATM_0354_phase2.Files/account_creation_requests.txt";
    private static final String ALERTS_FILE_NAME = "phase1/ATM_0354_phase1/ATM_0354_phase2.Files/alerts.txt";
    private static final String OUTGOING_FILE_NAME = "phase1/ATM_0354_phase1/ATM_0354_phase2.Files/outgoing.txt";
    private static final String ATM_FILE_NAME = "phase1/ATM_0354_phase1/ATM_0354_phase2.Files/atm.txt";

    public static void main(String[] args) throws IOException{
        atm = new ATM();
        ih = new InputHandler();

        Scanner fileIn = new Scanner(new File(PEOPLE_FILE_NAME));
        boolean firstTime = !fileIn.hasNext();
        if(firstTime) {
            atm.setDateTime(LocalDateTime.now());
            state = "SetUpBankManager";
        }
        else{
            Scanner atmFileIn = new Scanner(new File(ATM_FILE_NAME));
            String date = atmFileIn.nextLine();
            atm.setDateTime(LocalDateTime.parse(date));
            ArrayList<ATM_0354_phase2.CashObject> cash = new ArrayList<>();
            while(atmFileIn.hasNextLine()){ //update cash amounts
                String[] lineInput = atmFileIn.nextLine().split(",");
                cash.add(new ATM_0354_phase2.CashObject(BigDecimal.valueOf(Double.parseDouble(lineInput[0])), Integer.parseInt(lineInput[1])));
            }
            atm.cashHandler = new CashHandler(cash);
            ArrayList<ATM_0354_phase2.Transaction> transactions = parseTransactions();
            parseUsers(fileIn, transactions); //Also sets up accounts
            state = "Login";
        }

        generateUI(new Scanner(System.in)); //Set up for console input
    }

    private static final String TRANSACTIONSFILE = "phase1/ATM_0354_phase1/ATM_0354_phase2.Files/transactions.txt";
    private static ArrayList<ATM_0354_phase2.Transaction> parseTransactions() throws IOException{
        Scanner in = new Scanner(new File(TRANSACTIONSFILE));
        ArrayList<ATM_0354_phase2.Transaction> transactions = new ArrayList<>();
        while(in.hasNextLine()){
            String[] userTransactions = in.nextLine().split(",");
            String username = userTransactions[0];
            ATM_0354_phase2.User curUser = (ATM_0354_phase2.User)atm.getUser(username); //idk what this does
            for(int i=1; i<userTransactions.length; i+=5){
                String transactionType = userTransactions[i+1];
                int accountid = Integer.parseInt(userTransactions[i]);
                int receiverAccount = Integer.parseInt(userTransactions[i+2]);
                BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(userTransactions[i+3]));
                LocalDateTime date = LocalDateTime.parse(userTransactions[i+4]); //TODO: figure out why this is useful
                if (transactionType.equals("Bill")) {
                    ATM_0354_phase2.Transaction newBill = new Bill(accountid, receiverAccount, amount);
                    transactions.add(newBill);
                }
                else if(transactionType.equals("Transaction")){
                    ATM_0354_phase2.Transaction newTransaction = new ATM_0354_phase2.Transaction(accountid, receiverAccount, amount, false);
                    transactions.add(newTransaction);
                }
                else{
                    System.out.println("Error"); //TODO: better error message
                }
            }

        }
        return transactions;
    }
    private static void parseUsers(Scanner fileIn, ArrayList<ATM_0354_phase2.Transaction> transactions ) throws IOException {
        int maxAccountID = -1; //TODO: figure out what is happening with this stuff
        while(fileIn.hasNext()){
            String[] personInput = fileIn.nextLine().split(",");
            String userType = personInput[0];
            String username = personInput[1];
            String password = personInput[2];
            atm.createPerson(userType, username, password);
            if(userType.equals("User")){
                int defaultID = Integer.parseInt(personInput[3]);
                String[] accounts = Arrays.copyOfRange(personInput, 3, personInput.length);
                ATM_0354_phase2.User newUser =((ATM_0354_phase2.User)atm.getUser(username));
                for(int i=0; i<accounts.length; i+=4){
                    int accountID = Integer.parseInt(accounts[i]);
                    String accountType = accounts[i+1];
                    BigDecimal balance = BigDecimal.valueOf(Double.parseDouble(accounts[i+2]));
                    LocalDateTime dateOfCreation = LocalDateTime.parse(accounts[i+3]);

                    ArrayList<ATM_0354_phase2.Transaction> transactions1 = new ArrayList<>();
                    for(Transaction t: transactions)
                        if(t.getAccountFrom() == accountID ||t.getAccountTo()==accountID)
                            transactions1.add(t);
                    newUser.addAccount(accountType, accountID, balance, dateOfCreation, transactions1);
                }
                newUser.setPrimary(defaultID); //TODO: make sure this works
                newUser.setPrimaryAccount(newUser.getAccount(defaultID));
                newUser.accountFactory.setNextAccountId(maxAccountID);
            }
        }
    }
    private static void generateUI(Scanner in){
        while(!state.equals("Shutdown")) {
            state = ih.handleInput(state, in);
            clearScreen(); //TODO: see if there's a more elegant way to do this
        }
        shutdownATM();
//        reset();
    }
    private static void clearScreen(){
        for(int i=0; i<50; i++) {
            System.out.println();
        }
    }

    public static void overwriteRequests(){
        try {
            PrintWriter writer = new PrintWriter(ACCOUNT_REQUESTS_FILE_NAME);
            writer.print("");
            writer.close();
        } catch (IOException e) {
            System.out.println("IOException with Account Creation Requests file");
        }
    }

    //TODO: Check two methods below.

    // public static BigDecimal parseDeposits()
    public static List<String> parseDeposits(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(DEPOSIT_FILE_NAME));
            String line;

            List<String> all_deposits = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                all_deposits.add(line);
            }
            br.close();
            return all_deposits;

        } catch (Exception e) {
            System.out.println("No Deposits.");
            return null;
        }
    }

    public void outgoingHandlerThingy() {
        try {
            String outgoingPath = "phase1/ATM_0354_phase1/ATM_0354_phase2.Files/outgoing.txt";
            BufferedWriter br = new BufferedWriter(new FileWriter(outgoingPath, true));
            //todo: find appropriate class and replace str with vars.
            br.write("bill_name" + "," + "user" + "," + 555.55 + "," + "some LocalDateTime" + "\n");

            br.close();
        } catch (Exception e) {
            System.out.println("File does not exist.");
        }
    }

    private static void shutdownATM(){
        writeATM();
        writePeople();
        writeTransactions();
        //TODO: Write a whole bunch of other things too!
    }

    private static void writeATM(){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(ATM_FILE_NAME), false));
            writer.write(atm.getDateTime().toString());
            writer.newLine();

            for (CashObject cash : atm.cashHandler.getCash()){
                writer.write(cash.getCashValue() + "," + cash.getCount());
                writer.newLine();
            }
            writer.close();
        }
        catch(IOException e){
            System.out.println(e.toString());
            System.out.println("IOException when writing atm cash amounts to atm.txt");
        }
    }

    private static void writePeople(){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(PEOPLE_FILE_NAME), false));

            for (ATM_0354_phase2.Person person : atm.userHandler.users){
                if(person instanceof BankDaddy){
                    writer.write("BankManager," + person.getUsername() + "," + person.getPassword());
                    writer.newLine();
                }
                else if (person instanceof ATM_0354_phase2.User){
                    ATM_0354_phase2.User user = (ATM_0354_phase2.User) person;
                    writer.write(user.writeUser());
                    writer.newLine();
                }
            }
            writer.close();
        }
        catch(IOException e){
            System.out.println(e.toString());
            System.out.println("IOException when writing people to people.txt");
        }
    }

    private static void writeTransactions(){
        for (Person person : atm.userHandler.users){
            if(person instanceof ATM_0354_phase2.User){
                ATM_0354_phase2.User user = (User) person;
                user.writeTransactions();
            }
        }
    }

    private static void reset(){
        try{
            String[] paths = {PEOPLE_FILE_NAME, DEPOSIT_FILE_NAME, OUTGOING_FILE_NAME,
                    ATM_FILE_NAME, ALERTS_FILE_NAME, ACCOUNT_REQUESTS_FILE_NAME};
            BufferedWriter bw;
            for (String path : paths){
                bw = new BufferedWriter(new FileWriter(new File (path), false));
                bw.write("");
                bw.close();
            }
        }
        catch(IOException e){
            System.out.println(e.toString());
            System.out.println("IOException when resetting the program.");
        }
    }
}
