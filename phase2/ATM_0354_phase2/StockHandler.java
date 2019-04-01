package ATM_0354_phase2;

import org.json.JSONException;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;


public class StockHandler {
    private HashMap<String, BigDecimal> prices;

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
            if (!key.equals(""))
                updateStock(key);
        }
    }

    private String formatDate(){
        LocalDateTime date = Main.atm.getDateTime().minusMonths(3);
        HashSet<String> holidays = new HashSet<>(Arrays.asList("2018-01-01", "2018-01-15", "2018-02-19", "2018-03-30",
                "2018-05-28", "2018-07-04", "2018-09-03", "2018-11-22", "2018-12-05", "2018-12-25", "2019-01-01",
                "2019-02-18", "2019-04-19", "2019-05-27"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatDateTime = date.format(formatter);

        while(holidays.contains(formatDateTime) ||
                date.getDayOfWeek().equals(DayOfWeek.SUNDAY) || date.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
            date = date.minusDays(1);
            formatDateTime = date.format(formatter);
        }
        return formatDateTime;

    }

    public void updateStock(String key) {

        String formattedDate = formatDate();
        try {
            String API_KEY = "KJFQFRS9IL1YVZ9B";
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
            } catch (IOException | JSONException e) {
                System.out.println(e.toString());
            }
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
    }

}
