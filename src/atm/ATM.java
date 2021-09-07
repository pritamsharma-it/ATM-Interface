package atm;

import java.util.Scanner;

public class ATM {

	public static void main(String[] args) {
		
		//initialize scanner
		Scanner sc=new Scanner(System.in);
		
		//initialize bank
		Bank theBank=new Bank("Bank of Pritam");
		
		//add a user in the bank which also creates a savings account
		User aUser = theBank.addUser("Pritam", "Sharma", "1234");
		
		//add a checking account for our user
		Account newAccount = new Account("Current", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User curUser;
		while(true) {
			
			//stay in login prompt until successful login
			curUser=ATM.mainMenuPrompt(theBank, sc);
			
			//stay in main menu until user quits
			ATM.printUserMenu(curUser, sc);
		}
		
	}
	
	//login menu
	public static User mainMenuPrompt(Bank theBank, Scanner sc) {
		
		//initialization
		String userID;
		String pin;
		User authUser;
		
		//prompt the user for user ID and pin combo until they are correct
		do {
			
			System.out.printf("\n\nWELCOME to %s\n\n", theBank.getName());
			System.out.printf("Enter USER ID: ");
			userID=sc.nextLine();
			System.out.printf("Enter PIN: ");
			pin=sc.nextLine();
			
			//try to get user object corresponding to the id and pin combo
			authUser=theBank.userlogin(userID,  pin);
			if(authUser==null) {
				System.out.println("Incorrect USER ID and PIN\n" + "Please try again");				
			}
			
		}while(authUser==null);//continue looping until successful login
		
		return authUser;
		
	}
	
	public static void printUserMenu(User theUser, Scanner sc) {
		
		//print the summary of the users accounts
		theUser.printAccountsSummary();
		
		//initialize
		int choice;
		
		//user menu
		do {
			System.out.printf("WELCOME %s what would you like to do?\n",theUser.getFirstName());
			System.out.println(" 1. Show the Transaction History");
			System.out.println(" 2. Withdraw");
			System.out.println(" 3. Deposite");
			System.out.println(" 4. Transfer");
			System.out.println(" 5. Quit");
			System.out.print("\nEnter a Choice: ");
			choice = sc.nextInt();
			
			if(choice < 1 || choice > 5) {
				System.out.println("Invalid choice. Please choose 1-5");
			}
		}while(choice < 1 || choice > 5);
		
		//process the choice
		switch(choice) {
		
		case 1:
			ATM.showTransHistory(theUser, sc);
			break;
		case 2:
			ATM.withdrawlFunds(theUser, sc);
			break;
		case 3:
			ATM.depositeFunds(theUser, sc);
			break;
		case 4:
			ATM.transferFunds(theUser, sc);
			break;
		case 5:
			//gobble up the rest of the previous input line
			sc.nextLine();
			break;
		}
		
		//redisplay this menu when user wants to quit
		if(choice!=5) {
			ATM.printUserMenu(theUser, sc);
		}
		
	}
	
	//show the transaction history of an account
	public static void showTransHistory(User theUser, Scanner sc) {
		
		int theAcct;
		
		//get account whose transaction history to look at
		do {
			
			System.out.printf("Enter the number (1-%d) of the account" +
							"whose transactions you want to see : ",
							theUser.numAccounts());
			theAcct = sc.nextInt()-1;
			if(theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid Account, please try again.");
			}
		}while(theAcct < 0 || theAcct >= theUser.numAccounts());
		
		//print the transaction history
		theUser.printAcctTransHistory(theAcct);
		
	}
	
	
	//process transferring funds from 1 account to another
	public static void transferFunds(User theUser, Scanner sc) {
		
		//initialize
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		
		//get the account to transfer funds
		do {
			System.out.printf("Enter the number (1-%d) of the account"+
							"to transfer from: ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if(fromAcct<0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid Account, please try again.");
			}
		}while(fromAcct<0 || fromAcct >= theUser.numAccounts());
		acctBal=theUser.getAcctBalance(fromAcct);
		
		//get the account to transfer to
		do {
			System.out.printf("Enter the number (1-%d) of the account"+
							"to transfer to: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if(toAcct<0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid Account, please try again.");
			}
		}while(toAcct<0 || toAcct >= theUser.numAccounts());
		
		//get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (max Rs%.02f): RS.",
					acctBal);
			amount=sc.nextDouble();
			if(amount<0) {
				System.out.println("Amount mmust be greater than zero.");
			}
			else if(amount>acctBal) {
				System.out.printf("Amount mmust not be greater than \n" + 
			"balance of Rs%.02f.\n",acctBal);
			}
		}while(amount < 0 || amount>acctBal);
		
		//finally do the transfer
		theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
				"transfer to account %s", theUser.getAcctUID(toAcct)));
		theUser.addAcctTransaction(toAcct, amount, String.format(
				"transfer to account %s", theUser.getAcctUID(fromAcct)));
	}
	public static void withdrawlFunds(User theUser, Scanner sc) {
		//initialize
		int fromAcct;
		double amount;
		double acctBal;
		String memo;
		
		//get the account to transfer funds
		do {
			System.out.printf("Enter the number (1-%d) of the account "+
							"to withdraw from: ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if(fromAcct<0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid Account, please try again.");
			}
		}while(fromAcct<0 || fromAcct >= theUser.numAccounts());
		acctBal=theUser.getAcctBalance(fromAcct);
		
		//get the amount to transfer
		do {
			System.out.printf("Enter the amount to withdraw (max Rs%.02f): RS.",
					acctBal);
			amount=sc.nextDouble();
			if(amount<0) {
				System.out.println("Amount mmust be greater than zero.");
			}
			else if(amount>acctBal) {
				System.out.printf("Amount mmust not be greater than \n" + 
			"balance of Rs%.02f.\n",acctBal);
			}
		}while(amount < 0 || amount> acctBal);
		
		//gobble up the rest of the previous input line
		sc.nextLine();
		
		//get the memo
		System.out.print("Enter the memo: ");
		memo = sc.nextLine();
		
		//do the withdraw
		theUser.addAcctTransaction(fromAcct, -1*amount, memo);
	}
	
	//process to fund deposit into a account
	public static void depositeFunds(User theUser, Scanner sc) {
		
		//initialize
		int toAcct;
		double amount;
		double acctBal;
		String memo;
		
		//get the account to transfer funds
		do {
			System.out.printf("Enter the number (1-%d) of the account"+
							"to deposite in: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if(toAcct<0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid Account, please try again.");
			}
		}while(toAcct<0 || toAcct >= theUser.numAccounts());
		acctBal=theUser.getAcctBalance(toAcct);
		
		//get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (min Rs%.02f): RS.",
					acctBal);
			amount=sc.nextDouble();
			if(amount < 0) {
				System.out.println("Amount mmust be greater than zero.");
			}
			
		}while(amount < 0);
		
		//gobble up the rest of the previous input line
		sc.nextLine();
		
		//get the memo
		System.out.print("Enter the memo: ");
		memo = sc.nextLine();
		
		//do the withdraw
		theUser.addAcctTransaction(toAcct, amount, memo);
		
	}
}
