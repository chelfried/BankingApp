package org.sda.banking_app.account_menu;

import org.sda.banking_app.types.Account;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static org.sda.banking_app.account_menu.CreateAccount.createAccount;
import static org.sda.banking_app.account_menu.MakeCashDeposit.makeCashDeposit;
import static org.sda.banking_app.account_menu.MakeTransaction.makeTransaction;
import static org.sda.banking_app.account_menu.ViewTransactionHistory.viewTransactionHistory;
import static org.sda.banking_app.start_menu.StartMenu.loadStartMenu;
import static org.sda.banking_app.types.AccountDao.findAccounts;

public class AccountMenu {

    static final String INVALIDMESSAGE = "\033[0;31mInvalid choice.\033[0m\n";
    static final String IBANPREFIX = "RO04GRUP00009999";

    static final double RON_EUR = 0.205096;
    static final double RON_USD = 0.240900;
    static final double EUR_USD = 1.174700;

    protected static String activeUser;
    protected static List<Account> accountList;
    protected static int accountIndex;

    public static void loadAccountMenu() {
        System.out.printf("\n\n\u001B[7m\033[1;33m ACCOUNT MENU %39s \u001B[0m\n", activeUser);
        accountList = findAccounts(activeUser);
        int no = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = formatter.format(date);
        System.out.printf("\n\u001B[7m\033[1;36m Your Accounts%39s \u001B[0m\n", formattedDate);
        if (accountList.isEmpty()) {
            System.out.println("You do not have any open accounts yet.");
        } else {
            for (Account account : accountList) {
                no++;
                System.out.printf("[%d] ", no);
                System.out.printf("RO04GRUP00009999%d ", account.getAccountNo());
                System.out.printf("%-9s", account.getAccountType());
                System.out.printf("%10.2f ", account.getBalance());
                System.out.printf("(%s)", account.getCurrency());
                System.out.println();
            }
        }
        if (accountList.isEmpty()) {
            menuChoiceWhenNoAccounts();
        } else {
            menuChoice();
        }
    }

    public static void menuChoiceWhenNoAccounts() {
        System.out.println("\n[\033[1;33mC\u001B[0m] Create Account\n");
        System.out.println("[\033[1;33mL\u001B[0m] Log Out\n");
        Scanner input = new Scanner(System.in);
        String choice;
        do {
            System.out.print("Input: ");
            choice = input.nextLine();
            choice = choice.toUpperCase();
            switch (choice) {
                case "C":
                    createAccount();
                    break;
                case "L":
                    System.out.println();
                    setActiveUser(null);
                    loadStartMenu();
                    break;
                default:
                    System.out.println(INVALIDMESSAGE);
                    break;
            }
        }
        while (!(choice.equals("C") || choice.equals("L")));
    }

    public static void menuChoice() {
        System.out.println("\n[\033[1;33mM\u001B[0m] Make Transaction");
        System.out.println("[\033[1;33mV\u001B[0m] View Transaction History");
        System.out.println("[\033[1;33mC\u001B[0m] Create Account");
        System.out.println("[\033[1;33mD\u001B[0m] Deposit Cash\n");
        System.out.println("[\033[1;33mL\u001B[0m] Log Out\n");
        Scanner input = new Scanner(System.in);
        String choice;
        do {
            System.out.print("Input: ");
            choice = input.nextLine();
            choice = choice.toUpperCase();
            switch (choice) {
                case "M":
                    makeTransaction(accountList);
                    loadAccountMenu();
                    break;
                case "V":
                    viewTransactionHistory(accountList);
                    break;
                case "C":
                    createAccount();
                    break;
                case "D":
                    makeCashDeposit(accountList);
                    break;
                case "L":
                    System.out.println();
                    setActiveUser(null);
                    loadStartMenu();
                    break;
                default:
                    System.out.println(INVALIDMESSAGE);
                    break;
            }
        }
        while (!(choice.equals("M") || choice.equals("V") || choice.equals("C") || choice.equals("D") || choice.equals("L")));
    }

    public static void getIndexSelection() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("\nInput: ");
            try {
                accountIndex = input.nextInt();
                if (accountIndex <= accountList.size() && accountIndex > 0) {
                    break;
                } else {
                    System.out.println("\033[0;31mPlease enter a valid index number.\033[0m");
                }
            } catch (java.util.InputMismatchException e) {
                input.nextLine();
                System.out.println("\033[0;31mPlease enter a valid index number.\033[0m");
            }
        }
    }

    public static void setActiveUser(String username) {
        activeUser = username;
    }

    public static void goBackToAccountMenu() {
        System.out.print("\nPress [Enter] to go back to the Account Menu.");
        Scanner input = new Scanner(System.in);
        input.nextLine();
        loadAccountMenu();
    }

    private AccountMenu() {
        throw new IllegalStateException();
    }

}
