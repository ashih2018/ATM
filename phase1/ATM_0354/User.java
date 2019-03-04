package ATM_0354;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class User extends Person {

    private ArrayList<Account> accounts;
    private Date creationDate;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    public User(Date creationDate) {
        creationDate = new Date();
    }

    public String getCreationDate() {
        return dateFormat.format(creationDate);
    }

}
