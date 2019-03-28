package ATM_0354_phase2;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private static final String TRANSACTIONSFILE = "phase2/ATM_0354_phase2/Files/transactions.txt";

    public static void main(String[] args) throws IOException {
        atm = new ATM();
        ih = new InputHandler();

        Scanner fileIn = new Scanner(new File(PEOPLE_FILE_NAME));
        boolean firstTime = !fileIn.hasNext();
        if (firstTime) {
            atm.setDateTime(LocalDateTime.now());
            state = "SetUpBankManager";
        } else {
            Scanner atmFileIn = new Scanner(new File(ATM_FILE_NAME));
            String date = atmFileIn.nextLine();
            LocalDateTime curDate = LocalDateTime.parse(date).plusDays(1);
            nextDay(curDate);
            ArrayList<CashObject> cash = new ArrayList<>();
            while (atmFileIn.hasNextLine()) { //update cash amounts
                String[] lineInput = atmFileIn.nextLine().split(",");
                cash.add(new CashObject((Integer.parseInt(lineInput[0])), Integer.parseInt(lineInput[1])));
            }
            atm.cashHandler = new CashHandler(cash);
            parseUsers(fileIn); //Also sets up accounts
            parseTransactions();
            state = "Login";
        }

        generateUI(new Scanner(System.in)); //Set up for console input
    }
    private static void nextDay(LocalDateTime curDate){
        atm.setDateTime(curDate);
        //TODO: Add functionality for interest, month changing, etc
    }
    private static void parseTransactions() throws IOException {
        Scanner in = new Scanner(new File(TRANSACTIONSFILE));
        while (in.hasNextLine()) {
            String[] userTransactions = in.nextLine().split(",");
            String username = userTransactions[0];
            User curUser = (User) atm.getUser(username);
            String transactionType = userTransactions[1];
            Transaction newTransaction = null;
            ArrayList<Account> relevantAccs = new ArrayList<>();
            switch (transactionType) {
                case "Bill": {
                    String destination = userTransactions[2];
                    int accountFromId = Integer.parseInt(userTransactions[3]);
                    BigDecimal value = BigDecimal.valueOf(Double.parseDouble(userTransactions[4]));
                    LocalDateTime date = LocalDateTime.parse(userTransactions[5]);
                    newTransaction = new Bill(destination, curUser.getAccount(accountFromId), value, date);
                    relevantAccs.add(curUser.getAccount(accountFromId));
                    break;
                }
                case "Deposit": {
                    int accountToId = Integer.parseInt(userTransactions[2]);
                    BigDecimal value = BigDecimal.valueOf(Double.parseDouble(userTransactions[3]));
                    LocalDateTime date = LocalDateTime.parse(userTransactions[4]);
                    newTransaction = new Deposit(curUser.getAccount(accountToId), value, date);
                    relevantAccs.add(curUser.getAccount((accountToId)));
                    break;
                }
                case "Transfer": {
                    User userFrom = (User) Main.atm.getUser(userTransactions[2]);
                    int accountFromId = Integer.parseInt(userTransactions[3]);
                    User userTo = (User) Main.atm.getUser(userTransactions[4]);
                    int accountToId = Integer.parseInt(userTransactions[5]);
                    BigDecimal value = BigDecimal.valueOf(Double.parseDouble(userTransactions[6]));
                    LocalDateTime date = LocalDateTime.parse(userTransactions[7]);
                    newTransaction = new Transfer(userFrom.getAccount(accountFromId), userTo.getAccount(accountToId), value, date);
                    relevantAccs.add(userFrom.getAccount(accountFromId));
                    relevantAccs.add(userTo.getAccount(accountToId));
                    break;
                }
                case "Withdrawal": {
                    int accountFromId = Integer.parseInt(userTransactions[3]);
                    int value = Integer.parseInt(userTransactions[4]);
                    LocalDateTime date = LocalDateTime.parse(userTransactions[5]);
                    newTransaction = new Withdrawal(curUser.getAccount(accountFromId), value, date);
                    relevantAccs.add(curUser.getAccount(accountFromId));
                    break;
                }
                default:
                    System.out.println("transactions.txt has an invalid format");
                    break;
            }
            for(Account relevantAcc: relevantAccs){
                relevantAcc.addTransaction(newTransaction);
            }
        }
    }


    private static void parseUsers(Scanner fileIn) {
        int maxAccountID = -1;
        while (fileIn.hasNext()) {
            String[] personInput = fileIn.nextLine().split(",");
            String userType = personInput[0];
            String username = personInput[1];
            String password = personInput[2];
            String salt = personInput[3];
            atm.createPerson(userType, username, password, salt);
            if(userType.equals("User")){
                int defaultID = Integer.parseInt(personInput[4]);
                String[] accounts = Arrays.copyOfRange(personInput, 5, personInput.length);
                User newUser =((User)atm.getUser(username));
                newUser.removeAccount(0); //Removes the "primary account"
                System.out.println(Arrays.toString(accounts));
                int accountID = 0;
                for (int i = 0; i < accounts.length; i += 3) {
                    String accountType = accounts[i];
                    BigDecimal balance = BigDecimal.valueOf(Double.parseDouble(accounts[i + 1]));
                    System.out.println(balance);
                    LocalDateTime dateOfCreation = LocalDateTime.parse(accounts[i + 2]);
                    newUser.addAccount(accountType, accountID++, balance, dateOfCreation, new ArrayList<>());
                }
                newUser.setPrimary(defaultID);
                newUser.accountFactory.setNextAccountId(maxAccountID);
            }
        }
    }

    private static void generateUI(Scanner in) {
        while (!state.equals("Shutdown")) {
            state = ih.handleInput(state, in);
            clearScreen();
        }
        shutdownATM();
//        reset(); //Note: uncomment this for testing fresh startup
    }

    private static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static void overwriteRequests() {
        try {
            PrintWriter writer = new PrintWriter(ACCOUNT_REQUESTS_FILE_NAME);
            writer.print("");
            writer.close();
        } catch (IOException e) {
            System.out.println("IOException with Account Creation Requests file");
        }
    }

    public static List<String> parseDeposits() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(DEPOSIT_FILE_NAME));
            String line;

            List<String> all_deposits = new ArrayList<>();
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

    public void writeBill(String destination, BigDecimal amount) {
        try {
            String outgoingPath = "phase2/ATM_0354_phase2/Files/outgoing.txt";
            BufferedWriter br = new BufferedWriter(new FileWriter(outgoingPath, true));
            //todo: find appropriate class and replace str with vars.
            br.write("bill_name" + "," + "user" + "," + 555.55 + "," + "some LocalDateTime" + "\n");

            br.close();
        } catch (Exception e) {
            System.out.println("File does not exist.");
        }
    }

    private static void shutdownATM() {
        writeATM();
        writePeople();
        writeTransactions();
    }

    private static void writeATM() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(ATM_FILE_NAME), false));
            writer.write(atm.getDateTime().toString());
            writer.newLine();

            for (CashObject cash : atm.cashHandler.getCash()) {
                writer.write(cash.getCashValue() + "," + cash.getCount());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println("IOException when writing atm cash amounts to atm.txt");
        }
    }

    private static void writePeople() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(PEOPLE_FILE_NAME), false));
            for (Person person : atm.userHandler.users){
                if(person instanceof BankManager){
                    writer.write("BankManager," + person.getUsername() + "," + person.getHash()
                            + "," + person.getSalt());
                    writer.newLine();
                } else if (person instanceof User) {
                    User user = (User) person;
                    writer.write(user.writeUser());
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println("IOException when writing people to people.txt");
        }
    }

    private static void writeTransactions() {
        for (Person person : atm.userHandler.users) {
            if (person instanceof User) {
                User user = (User) person;
                user.writeTransactions();
            }
        }
    }

    private static void reset() {
        try {
            String[] paths = {PEOPLE_FILE_NAME, DEPOSIT_FILE_NAME, OUTGOING_FILE_NAME,
                    ATM_FILE_NAME, ALERTS_FILE_NAME, ACCOUNT_REQUESTS_FILE_NAME};
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
