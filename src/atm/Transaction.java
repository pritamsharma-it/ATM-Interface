package atm;

import java.util.Date;

public class Transaction {
	
	//Amount of this transaction
	private double amount;
	
	//time and date of transaction
	private Date timestamp;
	
	//memo of transaction
	private String memo;
	
	//account where transaction is performed
	private Account inAccount;
	
	
	//create new transaction
	public Transaction(double amount, Account inAccount) {
		
		this.amount= amount;
		this.inAccount=inAccount;
		this.timestamp=new Date();
		this.memo="";
		
	}
	
	//create new transaction
	public Transaction(double amount, String memo, Account inAccount) {
		
		//call the two argument constructor first
		this(amount, inAccount);
		
		this.memo=memo;
		
		
	}
	
	//get balance by adding all transactions
	public double getAmount() {
		return this.amount;
	}

	//get string summarizing  the transaction
	public String getSummaryLine() {
		if(this.amount>=0) {
			return String.format("%s : Rs%.02f : %s", this.timestamp.toString(),
					this.amount,this.memo);
		}
		else {
			return String.format("%s : Rs(%.02f) : %s", this.timestamp.toString(),
					-this.amount,this.memo);
		}
	}
}
