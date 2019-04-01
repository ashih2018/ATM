package ATM_0354_phase2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Parser {

    private static final String TRANSACTIONS_FILE = "phase2/ATM_0354_phase2/Files/transactions.txt";
    private static final String STOCKS_FILE_NAME = "phase2/ATM_0354_phase2/Files/stocks.txt";
    private static final String DEPOSIT_FILE_NAME = "phase2/ATM_0354_phase2/Files/deposits.txt";

    public void parseTransactions() throws IOException {
        Scanner in = new Scanner(new File(TRANSACTIONS_FILE));
        while (in.hasNextLine()) {
            String[] userTransactions = in.nextLine().split(",");
            String username = userTransactions[0];
            User curUser = (User) Main.atm.getUser(username);
            String transactionType = userTransactions[1];
            Transaction newTransaction = null;
            ArrayList<Account> relevantAccounts = new ArrayList<>();
            switch (transactionType) {
                case "Bill": {
                    String destination = userTransactions[2];
                    int accountFromId = Integer.parseInt(userTransactions[3]);
                    BigDecimal value = BigDecimal.valueOf(Double.parseDouble(userTransactions[4]));
                    LocalDateTime date = LocalDateTime.parse(userTransactions[5]);
                    newTransaction = new Bill(destination, curUser.getAccount(accountFromId), value, date);
                    relevantAccounts.add(curUser.getAccount(accountFromId));
                    break;
                }
                case "Deposit": {
                    int accountToId = Integer.parseInt(userTransactions[2]);
                    BigDecimal value = BigDecimal.valueOf(Double.parseDouble(userTransactions[3]));
                    LocalDateTime date = LocalDateTime.parse(userTransactions[4]);
                    newTransaction = new Deposit(curUser.getAccount(accountToId), value, date);
                    relevantAccounts.add(curUser.getAccount((accountToId)));
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
                    relevantAccounts.add(userFrom.getAccount(accountFromId));
                    relevantAccounts.add(userTo.getAccount(accountToId));
                    break;
                }
                case "Withdrawal": {
                    int accountFromId = Integer.parseInt(userTransactions[2]);
                    int value = Integer.parseInt(userTransactions[3]);
                    LocalDateTime date = LocalDateTime.parse(userTransactions[4]);
                    newTransaction = new Withdrawal(curUser.getAccount(accountFromId), value, date);
                    relevantAccounts.add(curUser.getAccount(accountFromId));
                    break;
                }
                case "Loan": {
                    int accountToId = Integer.parseInt(userTransactions[2]);
                    BigDecimal value = BigDecimal.valueOf(Double.parseDouble(userTransactions[3]));
                    BigDecimal interest = BigDecimal.valueOf(Double.parseDouble(userTransactions[4]));
                    LocalDateTime date = LocalDateTime.parse(userTransactions[5]);
                    LocalDateTime endDate = LocalDateTime.parse(userTransactions[6]);
                    newTransaction = new Loan(curUser.getAccount(accountToId), value, interest, date, endDate);
                    ((Loan) newTransaction).recordLoan();
                    curUser.changeLoanLimit(-1 * value.intValue());
                    break;
                }
                default:
                    System.out.println("transactions.txt has an invalid format");
                    break;
            }
            for (Account relevantAcc : relevantAccounts)
                relevantAcc.addTransaction(newTransaction);
        }
    }


    public void parseUsers(Scanner fileIn) {
        while (fileIn.hasNext()) {
            String[] personInput = fileIn.nextLine().split(",");
            String userType = personInput[0];
            String username = personInput[1];
            String password = personInput[2];
            String salt = personInput[3];
            Main.atm.createPerson(userType, username, password, salt);
            if (userType.equals("User")) {
                int defaultID = Integer.parseInt(personInput[4]);
                String[] accounts = Arrays.copyOfRange(personInput, 5, personInput.length);
                User newUser = ((User) Main.atm.getUser(username));
                newUser.removeAccount(0); //Removes the "primary account"
                int accountID = 0;
                for (int i = 0; i < accounts.length; i += 3) {
                    String accountType = accounts[i];
                    BigDecimal balance = BigDecimal.valueOf(Double.parseDouble(accounts[i + 1]));
                    LocalDateTime dateOfCreation = LocalDateTime.parse(accounts[i + 2]);
                    newUser.addAccount(accountType, accountID++, balance, dateOfCreation, new ArrayList<>());
                }
                newUser.setPrimary(defaultID);
                newUser.accountFactory.setNextAccountId(accountID-1);
            }
        }
    }

    public void parseStocks(){
        try{
            Scanner in = new Scanner(new File(STOCKS_FILE_NAME));

            if(in.hasNextLine()){   //if there have ever been any stock purchases
                String[] keys = in.nextLine().split(",");
                for(String key: keys){
                    Main.atm.stockHandler.addStock(key);
                }
                Main.atm.stockHandler.updateStocks();
            }
            while(in.hasNextLine()){ //if someone currently owns stock
                String[] stockInfo = in.nextLine().split(",");
                if(stockInfo.length > 1){
                    String username = stockInfo[0];
                    for(int i = 1; i < stockInfo.length - 1; i+=2){
                        String symbol = stockInfo[i];
                        int quantity = Integer.parseInt(stockInfo[i+1]);
                        User user = ((User) Main.atm.userHandler.getUser(username));
                        int id = user.getInvestmentAccountId();
                        ((InvestmentAccount) user.getAccount(id)).setupStock(symbol, quantity);
                    }
                }

            }
        }
        catch(IOException e){
            System.out.println(e.toString());
        }
    }

    public List<String> parseDeposits() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(DEPOSIT_FILE_NAME));
            String line;

            List<String> all_deposits = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                all_deposits.add(line);
            }
            br.close();
            return all_deposits;

        } catch (IOException e) {
            System.out.println("No Deposits.");
            return new ArrayList<>();
        }
    }

}
