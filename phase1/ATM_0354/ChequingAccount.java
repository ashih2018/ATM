package ATM_0354;

public class ChequingAccount extends AssetAccount {

    private boolean primary;

    public ChequingAccount(int id) {
        super(id);
        this.primary = false;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isPrimary() {
        return primary;
    }
}
