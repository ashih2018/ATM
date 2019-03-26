package ATM_0354_phase1;

import java.math.BigDecimal;

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
