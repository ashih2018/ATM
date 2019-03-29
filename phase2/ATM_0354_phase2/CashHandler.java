package ATM_0354_phase2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class CashHandler{

    private ArrayList<CashObject> cash;
    private boolean hasEnoughBills;

    /**
    Precondition: the CashObjects in cash all have different bill values.
     */
    public CashHandler(ArrayList<CashObject> cash){
        this.cash = cash;
    }

    public CashHandler(){
        this.cash = new ArrayList<>();
        this.hasEnoughBills = false;
    }

    public void addCash(int billValue, int count){
        for(CashObject cashObject : cash){
            if (cashObject.getCashValue() == billValue){
                cashObject.setCount(cashObject.getCount() + count);
                updateRestockStatus();
                return;
            }
        }
        CashObject cashObject = new CashObject(billValue, count);
        this.cash.add(cashObject);
        updateRestockStatus();
    }
    public int getTotalCash(){
        int total = 0;
        for (CashObject cash1 : this.cash) {
            total += cash1.getCashValue() * cash1.getCount();
        }
        return total;
    }
    public boolean withdrawCash(int money){
        if(money%5 != 0){
            System.out.println("Invalid amount to withdraw");
            return false;
        }
        if(getTotalCash() < money){
            System.out.println("The ATM doesn't have that much money.");
            return false;
        }
        this.cash.sort(Collections.reverseOrder());
        for(CashObject cash1: this.cash){
            int numBills = Math.min(money / cash1.getCashValue(), cash1.getCount());
            money -= numBills*cash1.getCashValue();
            cash1.setCount(cash1.getCount()-numBills);
        }

        return money == 0;
    }

    private void updateRestockStatus(){
        for(CashObject cashObject: cash){
            if (cashObject.needsRestocking()) {
                sendAlert();
                this.hasEnoughBills = false;
                return;
            }
        }

        this.hasEnoughBills = true;
    }

    public boolean hasEnoughBills(){
//        for(CashObject cash: bills.keySet()){
//            int numBills = bills.get(cash);
//            if(numBills > )
//        }
        return hasEnoughBills;
    }

    public void sendAlert(){
        try {
            File file = new File("phase2/ATM_0354_phase2/Files/alerts.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for(CashObject c : cash) {
                writer.write(c.getCashValue() + ", " + c.getCount());
                writer.newLine();
            }

            writer.close();
        }
        catch (Exception e) {
            System.out.println("ERROR: Could not create file.");
        }
    }

    public ArrayList<CashObject> getCash(){
        return cash;
    }

}
