CSC 207 ATM Project
Group 0354: Alex Shih, Sky Li, Stella Cai, Labib Zaman, Samarth Patel

Description:
Our program models an ATM, Automated Teller Machine. Our ATM shares most of the functionality with an ATM you would
find at a bank. Once an account is created by the bank manager, a user can view their account(s), transfer money between
accounts they own, send and receive money, pay bills. Our ATM currently allows user to create either a debt account,
which includes credit card accounts and line of credit accounts, or an asset account, which includes a checking
accounts and savings accounts.

Notes:
- If the user does not have an account, they must go to view accounts to request to create an account.
- The first chequing account created is defaulted as the primary chequing account.
- All storable information in the ATM is stored under Files.

Bank Manager Actions:
- create users: Creates a new user with a unique username and password
- approve account creation requests: If there is a request to open an account, the Bank Manager can choose whether
or not to approve the request to open the new account
- refill cash: Increases the number of bills in the machine.
- undo recent transactions: The Bank Manager can choose whether or not to undo the most recent transaction for a
certain user's account if that transaction is not a bill.
- logout: Logs out of the Bank Manager's account.
- shutdown: Shuts down the ATM and the program.

User Actions:
- view accounts: Shows the user a list of their accounts and account information. Account information includes
account ID and account balance.
- withdraw money: Allows the user to withdraw money from an account they own, unless if the account is a credit
card account.
- deposit money: Allows the user to deposit money to any account they own.
- transfer money: Allows the user to transfer money between any two accounts they own or to another user's account,
as long as the user has created an account in the ATM.
- pay bills: Allows the user to pay a bill to a non-user's account. These transactions cannot be undo'ed.
- logout: Logs out of the current user's account.
- exit: Exits the program and shuts down the ATM.

Usage:
1. To run the ATM, run Main.java.
2. If it is the first time this program is being run, the ATM will assume that you are the bank manager and
will make you create an account for the BankManager, with a username and a password.
This account will have privileges other accounts will not have such as creating a bank account for a user and
un-doing transactions.
3. After the creation of the Bank Manager account, you will have to sign in using the username and password you have
just created.
4. Once signed in as the Bank Manager, there will be a list of actions that the Bank Manager can perform, which are
specified above under Bank Manager actions.
5. If a user is signed in, there will be a list of actions that the user can perform, which are specified above under
User Actions.
6. Users and BankManager will be able to logout of their account with the logout action.
7. The program will end when the BankManager performs the "shutdown" action.
