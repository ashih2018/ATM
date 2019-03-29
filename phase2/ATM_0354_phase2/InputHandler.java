package ATM_0354_phase2;


import java.util.*;
import ATM_0354_phase2.inputMethods.*;
public class InputHandler {
    private Map<String, InputMethod> inputMap;

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

        inputMap.put("ProcessEmailCheques", new ProcessEmailChequesMethod());

        inputMap.put("UserOptions", new UserOptionsMethod());
        inputMap.put("BankEmployeeOptions", new BankEmployeeOptionsMethod());
        //User Options
        inputMap.put("AccountView", new AccountViewMethod());
        inputMap.put("RequestNewAccount", new RequestNewAccountMethod());
        inputMap.put("WithdrawMoney", new WithdrawMoneyMethod());
        inputMap.put("DepositMoney", new DepositMoneyMethod());
        inputMap.put("TransferMoney", new TransferMoneyMethod());
        inputMap.put("PayBills", new PayBillsMethod());
        inputMap.put("ChangePassword", new ChangePasswordMethod());
        inputMap.put("SendEmailSummary", new EmailSummaryMethod());

        inputMap.put("Logout", new LogoutMethod());
    }

    String handleInput(String input, Scanner in){
        return inputMap.get(input).run(in);
    }

}

