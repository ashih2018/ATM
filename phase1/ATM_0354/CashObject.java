package ATM_0354;

public class CashObject {

    private int cashValue;
    private int count;

    public CashObject(int cashValue, int count){
        this.cashValue = cashValue;
        this.count = count;
    }

    public int getCashValue() {
        return cashValue;
    }

    public void setCashValue(int cashValue) {
        this.cashValue = cashValue;
    }
}
