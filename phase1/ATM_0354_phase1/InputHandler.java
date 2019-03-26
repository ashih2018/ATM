package ATM_0354_phase1;
import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.inputMethods.ChangePasswordMethod;

import java.util.*;

public class InputHandler {
    private Map<String, InputMethod> inputMap;

    InputHandler(){
        inputMap = new HashMap<>();
        inputMap.put("SetUpBankManager", new ATM_0354_phase2.inputMethods.SetUpBankManagerMethod());
        inputMap.put("Login", new ATM_0354_phase2.inputMethods.LoginMethod());
        inputMap.put("BankManagerOptions", new ATM_0354_phase2.inputMethods.BankManagerOptionsMethod());

        //BankManager Options
        inputMap.put("UserCreationScreen", new ATM_0354_phase2.inputMethods.UserCreationScreenMethod());
        inputMap.put("IndividualAccountApprove", new ATM_0354_phase2.inputMethods.IndividualAccountApproveMethod());
        inputMap.put("RefillCash", new ATM_0354_phase2.inputMethods.RefillCashMethod());
        inputMap.put("UndoTransaction", new ATM_0354_phase2.inputMethods.UndoTransactionMethod());

        inputMap.put("UserOptions", new ATM_0354_phase2.inputMethods.UserOptionsMethod());
        //User Options
        inputMap.put("AccountView", new ATM_0354_phase2.inputMethods.AccountViewMethod());
        inputMap.put("RequestNewAccount", new ATM_0354_phase2.inputMethods.RequestNewAccountMethod());
        inputMap.put("WithdrawMoney", new ATM_0354_phase2.inputMethods.WithdrawMoneyMethod());
        inputMap.put("DepositMoney", new ATM_0354_phase2.inputMethods.DepositMoneyMethod());
        inputMap.put("TransferMoney", new ATM_0354_phase2.inputMethods.TransferMoneyMethod());
        inputMap.put("PayBills", new ATM_0354_phase2.inputMethods.PayBillsMethod());
        inputMap.put("ChangePassword", new ChangePasswordMethod());

        inputMap.put("Logout", new ATM_0354_phase2.inputMethods.LogoutMethod());
    }

    String handleInput(String input, Scanner in){
        return inputMap.get(input).run(in);
    }

}

