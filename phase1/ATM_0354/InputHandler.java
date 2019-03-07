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
                ~First time setup - bank manager account~
                ~Login Screen~
                ~Bank manager options~
                    ~User creation screen~
                    Account creation request screen
                        Individual request approve / decline
                    Refill cash screen
                    Undo recent transaction screen
                    ~Logout~
                ~User screen~
                    ~Account view summary screen/account manager~
                         ~Request new accounts screen~
                    ~Withdraw money screen - shows all accounts + asks which to withdraw from + asks how much~
                    Deposit money - amount + account (optional) - using file
                    Transfer money screen - asks to what account / to who
                    Pay bills screen
                    ~Logout~

         */
    InputHandler(){
        inputMap = new HashMap<>();
        inputMap.put("SetUpBankManager", new SetUpBankManagerMethod());
        inputMap.put("Login", new LoginMethod());
        inputMap.put("BankManagerOptions", new BankManagerOptionsMethod());

        //BankManager Options
        inputMap.put("UserCreationScreen", new UserCreationScreenMethod());
        inputMap.put("IndividualAccountApprove", new IndividualAccountApproveMethod());
        inputMap.put("RefillCash", new RefillCashMethod());
        inputMap.put("UndoTransaction", new UndoTransactionMethod());

        inputMap.put("UserOptions", new UserOptionsMethod());
        //User Options
        inputMap.put("AccountView", new AccountViewMethod());
        inputMap.put("RequestNewAccount", new RequestNewAccountMethod());
        inputMap.put("WithdrawMoney", new WithdrawMoneyMethod());
        inputMap.put("DepositMoney", new DepositMoneyMethod());
        inputMap.put("TransferMoney", new TransferMoneyMethod());
        inputMap.put("PayBills", new PayBillsMethod());
        inputMap.put("ChangePassword", new ChangePasswordMethod());

        inputMap.put("Logout", new LogoutMethod());
    }

    String handleInput(String input, Scanner in){
        return inputMap.get(input).run(in);
    }

}

