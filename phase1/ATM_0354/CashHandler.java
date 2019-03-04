package ATM_0354;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class CashHandler implements Observer {

    private ArrayList<CashObject> cash = new ArrayList<>();

    @Override
    public void update(Observable o, Object arg) {
        
    }
}
