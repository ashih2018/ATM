package ATM_0354_phase2;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;


public class StockHandler {
    private HashMap<String, BigDecimal> prices;
    private final String API_KEY = "KJFQFRS9IL1YVZ9B";
    public StockHandler() {
        prices = new HashMap<>();
    }

    public BigDecimal getPrice(String key) {
        return prices.get(key);
    }


    public ArrayList<String> getKeys() {
        return new ArrayList<>(prices.keySet());
    }

    public void addStock(String key) {
        prices.put(key, BigDecimal.ZERO);
    }

    /**
     * Updates the price of each stock based on the closing value of the stock 5 years before the ATM's date.
     */
    public void updateStocks() {
        for (String key : prices.keySet()) {
            updateStock(key);
        }
    }

    public void updateStock(String key) {
        LocalDateTime date = Main.atm.getDateTime().minusMonths(3);
        if(date.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
            date = date.minusDays(1);
        }
        else if(date.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
            date = date.minusDays(2);
        }
        int monthValue = date.getMonthValue();
        String month = "" + date.getMonthValue();
        if (monthValue + 1 <10){
            month  = "0" + monthValue;
        }
        String formattedDate = "" + date.getYear() + "-" + month + "-" + date.getDayOfMonth();
        try {
            URL url = new URL(
                    "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&" +
                            "symbol=" + key + "&apikey=" + API_KEY);
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
                BigDecimal closingValue = new BigDecimal((String) obj.getJSONObject("Time Series (Daily)")
                        .getJSONObject(formattedDate).get("4. close"));
                prices.put(key, closingValue);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
    }

}
