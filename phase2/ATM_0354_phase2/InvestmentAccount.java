package ATM_0354_phase2;

import java.math.BigDecimal;
import java.util.HashMap;

public class InvestmentAccount extends AssetAccount {

    // Key of stock, quantity of stock
    public HashMap<String, Integer> stocks = new HashMap<>();

    public InvestmentAccount(String username, int id) {
        super(username, id);
        setMinimumAllowedBalance(BigDecimal.ZERO);
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

    public void addStock(String key, String name, int quantity){
        if(!Main.atm.stockHandler.getKeys().contains(key)){
            Main.atm.stockHandler.addStock(key, name);
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

    public String getInvestmentPortfolio(){
        String str = "Investment Portfolio:\n" +
                "Balance: " + getBalance() + "\n" +
                "Total Asset Value: " + getTotalValue() + "\n" +
                "------------------------------";
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


}
