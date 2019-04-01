package ATM_0354_phase2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CashHandler {

    private ArrayList<CashObject> cash;
    private boolean hasEnoughBills;

    /**
     * Precondition: the CashObjects in cash all have different bill values.
     */
    CashHandler(ArrayList<CashObject> cash) {
        this.cash = cash;
        int[] values = {5, 10, 20, 50};
        for (int val : values) {
            boolean exists = false;
            for (CashObject cash1 : cash)
                if (cash1.getCashValue() == val)
                    exists = true;
            if (!exists)
                this.cash.add(new CashObject(val, 0));
        }
    }

    CashHandler() {
        this.cash = new ArrayList<>();
        this.hasEnoughBills = false;
    }

    public void addCash(int billValue, int count) {
        for (CashObject cashObject : cash) {
            if (cashObject.getCashValue() == billValue) {
                cashObject.setCount(cashObject.getCount() + count);
                updateRestockStatus();
                return;
            }
        }
        CashObject cashObject = new CashObject(billValue, count);
        this.cash.add(cashObject);
        updateRestockStatus();
    }

    public int getTotalCash() {
        int total = 0;
        for (CashObject cash1 : this.cash) {
            total += cash1.getCashValue() * cash1.getCount();
        }
        return total;
    }

    void setCash(HashMap<Integer, Integer> cashValues) {
        this.cash.clear();
        for (Map.Entry<Integer, Integer> entry : cashValues.entrySet())
            this.addCash(entry.getKey(), entry.getValue());
    }

    boolean withdrawCash(int money) {
        HashMap<Integer, Integer> original = new HashMap<>();
        for (CashObject cash1 : this.cash)
            original.put(cash1.getCashValue(), cash1.getCount());
        if (money % 5 != 0) {
            System.out.println("Invalid amount to withdraw");
            return false;
        }
        if (getTotalCash() < money) {
            System.out.println("The ATM doesn't have that much money.");
            return false;
        }
        this.cash.sort(Collections.reverseOrder());
        for (CashObject cash1 : this.cash) {
            int numBills = Math.min(money / cash1.getCashValue(), cash1.getCount());
            money -= numBills * cash1.getCashValue();
            cash1.setCount(cash1.getCount() - numBills);
        }
        if (money != 0) {
            this.setCash(original);
        }
        updateRestockStatus();
        return money == 0;
    }

    private void updateRestockStatus() {
        for (CashObject cashObject : cash) {
            if (cashObject.needsRestocking()) {
                sendAlert();
                this.hasEnoughBills = false;
                return;
            }
        }

        this.hasEnoughBills = true;
    }


    public void sendAlert() {
        try {
            File file = new File("phase2/ATM_0354_phase2/Files/alerts.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formatDateTime = Main.atm.getDateTime().format(formatter);
            writer.write("ATM needs restocking: " + formatDateTime);
            for (CashObject c : cash) {
                writer.newLine();
                writer.write(c.getCashValue() + ", " + c.getCount());
            }
            writer.newLine();
            writer.write("------------------------------------------");
            writer.newLine();

            writer.close();
        } catch (Exception e) {
            System.out.println("ERROR: Could not create file.");
        }
    }

    public ArrayList<CashObject> getCash() {
        return cash;
    }

    String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== Bill Count =====\n");
        for (CashObject cash1 : this.cash)
            sb.append(String.format("%d: %d\n", cash1.getCashValue(), cash1.getCount()));

        return sb.toString();
    }
}
