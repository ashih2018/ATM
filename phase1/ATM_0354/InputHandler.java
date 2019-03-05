package ATM_0354;
import ATM_0354.inputMethods.*;

import java.util.*;

public class InputHandler {
    private Map<String, InputMethod> inputMap;
    /*
    Possible Actions:
        First time setting up program:
            Set up Bank Manager
            Set up time <-- done automatically
            Set up initial cash in ATM <-- done automatically
        On Day start
            Login <-- done
        BankManager options:
            Set up a new User (username, password, account(s), initial deposit?)
            Check for account creation requests
                Approve
                Decline
            Refill cash
            Undo most recent transaction
            Logout
        User Actions
           Request new account
           View Accounts
                Withdraw moneyTransfer money
                Deposit money
           Deposit money into a checking account (default)
            Logout

     */
    /* Screens / States:
                First time setup - bank manager account
                Login Screen
                Bank manager options
                    User creation screen
                    Account creation request screen
                        Individual request approve / decline
                    Refill cash screen
                    Undo recent transaction screen
                User screen
                    Request new account screen
                    Account view summary screen/account manager
                         Request new accounts screen
                    Withdraw money screen - shows all accounts + asks which to withdraw from
                        Individual account withdrawal - asks how much
                    Deposit money - amount + account (optional) - using file
                    Transfer money screen - asks to what account / to who
                    Pay bills screen

         */
    public InputHandler(){
        inputMap = new HashMap<>();
        InputMethod setupBM = new SetUpBankManagerMethod();
        inputMap.put("SetUpBankManager", setupBM);
        InputMethod login = new LoginMethod();
        inputMap.put("Login", login);
        InputMethod bankManagerOptions = new BankManagerOptionsMethod();
        inputMap.put("BankManagerOptions", bankManagerOptions);
        InputMethod userCreation = new UserCreationScreenMethod();
        inputMap.put("UserCreationScreen", userCreation);
    }

    public static String handleInput(String input){
        return "";
    }

}

