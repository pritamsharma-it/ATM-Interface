package atm;

import java.util.ArrayList;

public class Account {
	
	//name of the account
	private String name;
	
	//accounts ID
	private String uID;
	
	//user that owns this account
	private User holder;
	
	//list of transaction on this account
	private ArrayList<Transaction> transactons;
	
	public Account(String name, User holder, Bank theBank) {
		
		//set account name and holder
		this.name = name;
		this.holder = holder;
		
		//get new account uID
		this.uID = theBank.getNewAccountUID();
		
		//initialize transactions
		this.transactons = new ArrayList<Transaction>();
		
		
	}
	
	//return account ID
	public String getuID() {
    	return this.uID;
    }
	
	//get summary line for account
	public String getSummaryLine() {
		
		//get accounts balance
		double balance = this.getBalance();
		
		//format the summary line depending on whether the balance is negative
		if(balance>=0) {
			return String.format("%s : Rs%.02f : %s", uID, balance, this.name);
		}
		else {
			
			return String.format("%s : Rs(%.02f) : %s", uID, balance, this.name);
			
		}
	}
		
	public double getBalance() {
		
		double balance=0;
		for(Transaction t : this.transactons) {
			balance += t.getAmount();
		}
		
		return balance;
	}
	
	//print the transaction history of the account
	public void printTransHistory() {
		
		System.out.printf("\nTransaction history for account %s\n", 
				this.uID);
		for(int j = this.transactons.size()-1;j>=0;j--) {
			System.out.println(this.transactons.get(j).getSummaryLine());
		}
		System.out.println();
		
	}
	
	//add new transaction in a account
	public void addTransaction(double amount, String memo) {
		
		//create new transaction object and add it to out list
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactons.add(newTrans);
	}
	
}
