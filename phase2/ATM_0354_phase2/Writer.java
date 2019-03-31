package ATM_0354_phase2;

import java.io.*;

public class Writer {

    private static final String PEOPLE_FILE_NAME = "phase2/ATM_0354_phase2/Files/people.txt";
    private static final String ACCOUNT_REQUESTS_FILE_NAME = "phase2/ATM_0354_phase2/Files/account_creation_requests.txt";
    private static final String ATM_FILE_NAME = "phase2/ATM_0354_phase2/Files/atm.txt";
    private static final String STOCKS_FILE_NAME = "phase2/ATM_0354_phase2/Files/stocks.txt";

    public void overwriteRequests() {
        try {
            PrintWriter writer = new PrintWriter(ACCOUNT_REQUESTS_FILE_NAME);
            writer.print("");
            writer.close();
        } catch (IOException e) {
            System.out.println("IOException with Account Creation Requests file");
        }
    }

    public void writeATM() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(ATM_FILE_NAME), false));
            writer.write(Main.atm.getDateTime().toString());
            writer.newLine();

            for (CashObject cash : Main.atm.cashHandler.getCash()) {
                writer.write(cash.getCashValue() + "," + cash.getCount());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println("IOException when writing atm cash amounts to atm.txt");
        }
    }

    public void writePeople() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(PEOPLE_FILE_NAME), false));
            for (Person person : Main.atm.userHandler.users) {
                if (person instanceof BankManager) {
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

    public void writeTransactions() {
        for (Person person : Main.atm.userHandler.users) {
            if (person instanceof User) {
                User user = (User) person;
                user.writeTransactions();
            }
        }
    }

    public void writeStocks() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(STOCKS_FILE_NAME), false));
            String keys = Main.atm.stockHandler.getKeys().toString();
            keys = keys.substring(1, keys.length() - 1).replaceAll(" ", "");
            writer.write(keys);
            writer.newLine();
            for (Person person : Main.atm.userHandler.users) {
                if (person instanceof User) {
                    User user = (User) person;
                    writer.write(user.writeInvestmentPortfolio());
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println("IOException when writing people to stocks.txt");
        }
    }

}
