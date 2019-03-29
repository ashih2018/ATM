package ATM_0354_phase2;

import java.math.BigDecimal;
import java.util.HashMap;

public class InvestmentAccount extends AssetAccount {

    // Key of stock, quantity of stock
    public HashMap<String, Integer> stocks = new HashMap<>();

    public InvestmentAccount(String username, int id) {
        super(username, id);
    }

    public BigDecimal getTotalValue(){
        BigDecimal value = getBalance();
        for(String key : stocks.keySet()){
            value = value.add(Main.atm.stockHandler.getPrice(key).multiply(new BigDecimal(stocks.get(key))));
        }
        return value;
    }


}
