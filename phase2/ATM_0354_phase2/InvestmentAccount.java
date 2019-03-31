package ATM_0354_phase2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class InvestmentAccount extends AssetAccount {

    // Key of stock, quantity of stock
    public HashMap<String, Integer> stocks = new HashMap<>();

    public InvestmentAccount(String username, int id) {
        super(username, id);
        setMinimumAllowedBalance(BigDecimal.ZERO);
    }
    public InvestmentAccount(String username, int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions){
        super(username, id, balance, dateOfCreation, transactions);
        this.setMinimumAllowedBalance(BigDecimal.ZERO);
    }

    public String writeInvestmentAccount(){
        StringBuilder str = new StringBuilder(getId());
        for (String key: stocks.keySet()){
            str.append(",");
            str.append(key);
            str.append(",");
            str.append(stocks.get(key));
        }
        return str.toString();
    }

    /**
     * For startup purposes
     */
    public void setupStock(String key, int quantity){
        stocks.put(key, quantity);
    }

    public InvestmentAccount(String username, int id, HashMap<String, Integer> stocks){
        super(username, id);
        this.stocks = stocks;
        setMinimumAllowedBalance(BigDecimal.ZERO);
    }

    public BigDecimal getTotalValue(){
        BigDecimal value = getBalance();
        for(String key : stocks.keySet()){
            value = value.add(Main.atm.stockHandler.getPrice(key).multiply(new BigDecimal(stocks.get(key))));
        }
        return value;
    }

    /*Todo get rid of the name in stockhandler
     *
     */
    public void buyStock(String key, int quantity){
        if(!Main.atm.stockHandler.getKeys().contains(key)){
            Main.atm.stockHandler.addStock(key);
            Main.atm.stockHandler.updateStock(key);
        }
        try{
            transferMoneyOut(Main.atm.stockHandler.getPrice(key).multiply(new BigDecimal(quantity)));
            if(stocks.containsKey(key)){
                stocks.put(key, stocks.get(key) + quantity);
            }
            else{
                stocks.put(key, quantity);
            }

        }
        catch(MoneyTransferException e){
            System.out.println("Insufficient funds to buy stock.");
        }
    }

    public void sellStock(String key, int quantity){
        if(stocks.keySet().contains(key) && stocks.get(key) >= quantity){
            try{
                transferMoneyIn(Main.atm.stockHandler.getPrice(key).multiply(new BigDecimal(quantity)));
                if(stocks.get(key) == quantity){
                    stocks.remove(key);
                }
                else{
                    stocks.put(key, (stocks.get(key) - quantity));
                }
            }
            catch(MoneyTransferException e){
                System.out.println(e.toString());
            }
        }
        else{
            System.out.println("Insufficient stocks to sell.");
        }

    }

    public String getInvestmentPortfolio(){
        String str = "Investment Portfolio:\n" +
                "Balance: " + getBalance() + "\n" +
                "Total Asset Value: " + getTotalValue() + "\n" +
                "------------------------------\n";
        StringBuilder sb = new StringBuilder(str);
        for (String key : stocks.keySet()){
            sb.append(key);
            sb.append("\t");
            sb.append(stocks.get(key));
            sb.append("\t");
            sb.append(Main.atm.stockHandler.getPrice(key));
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Investment Account " + "\n" + super.toString();
    }


}
