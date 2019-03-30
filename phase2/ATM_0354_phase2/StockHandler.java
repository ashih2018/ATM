package ATM_0354_phase2;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;


public class StockHandler {
    private HashMap<String, BigDecimal> prices;
    private HashMap<String, String> names;
    private final String API_KEY = "KJFQFRS9IL1YVZ9B";
    public StockHandler() {
        prices = new HashMap<>();
        names = new HashMap<>();
    }

    public StockHandler(HashMap<String, BigDecimal> prices, HashMap<String, String> names) {
        this.prices = prices;
        this.names = names;
    }

    public BigDecimal getPrice(String key) {
        return prices.get(key);
    }

    public String getName(String key) {
        return names.get(key);
    }

    public ArrayList<String> getKeys() {
        return new ArrayList<>(prices.keySet());
    }

    public void addStock(String key, String name) {
        prices.put(key, null);
        names.put(key, name);
    }

    /**
     * Updates the price of each stock based on the closing value of the stock 5 years before the ATM's date.
     */
    public void updateStocks() {
        LocalDateTime date = Main.atm.getDateTime();
        String formattedDate = "" + (date.getYear() - 5) + "-" + date.getMonth() + "-" + date.getDayOfMonth();
        for (String key : prices.keySet()) {
            updateStock(key);
        }
    }

    public void updateStock(String key) {
        LocalDateTime date = Main.atm.getDateTime();
        String formattedDate = "" + (date.getYear() - 5) + "-" + date.getMonth() + "-" + date.getDayOfMonth();
        try {
            URL url = new URL(
                    "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&" +
                            "symbol=" + key + "&outputsize=full&apikey=" + API_KEY);
            try {
                URLConnection connection = url.openConnection();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuilder input = new StringBuilder();
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    input.append(inputLine);
                }
                br.close();
                JSONObject obj = new JSONObject(input.toString());
                BigDecimal closingValue = (BigDecimal)
                        obj.getJSONObject("Time Series (Daily)").getJSONObject(formattedDate).get("4. close");
                prices.put(key, closingValue);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
    }

}
