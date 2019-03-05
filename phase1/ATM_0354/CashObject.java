package ATM_0354;

import java.math.BigDecimal;
import java.util.Observable;

public class CashObject{

    private final BigDecimal cashValue;
    private int count;

    public CashObject(BigDecimal cashValue, int count){
        this.cashValue = cashValue;
        this.count = count;
    }

    public BigDecimal getCashValue() {
        return cashValue;
    }

    public int getCount(){
        return count;
    }

    public void setCount(int count){
        this.count = count;
    }

    public boolean needsRestocking(){
        int restockLimit = 20;
        return count < restockLimit;
    }
}
