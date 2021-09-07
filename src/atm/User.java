package atm;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
	//firstname
	private String firstName;
	
	//lastname
    private String lastName;
    
    //ID of user
    private String uID;
    
    //MD5 hash for users pin no.
    private byte pinHash[];
    
    //list of accounts for this user
    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank theBank) {
    	
    	this.firstName=firstName;
    	this.lastName=lastName;
    	
    	//store the pins MD5 hash rather than the original value, for security reasons
    	
    	try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
    	
    	//get a new uniqque ID for user
    	this.uID = theBank.getNewUserUID();
    	
    	//create empty list of accounts
    	this.accounts = new ArrayList<Account>();
    	
    	//print message
    	System.out.printf(" New User:%s %s with ID %s created:\n", firstName, lastName, this.uID);
    	
    }
    
    //add account for the user
    public void addAccount(Account anAcct) {
    	this.accounts.add(anAcct);
    }
    
    //return user ID
    public String getuID() {
    	return this.uID;
    }

    //check whether the given pin matches the true user pin
    public boolean validatePin(String apin) {
    	
    	try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(apin.getBytes()),this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
    	return false;
    }

    //return users first name
    public String getFirstName() {
    	return this.firstName;
    }
    
    public void printAccountsSummary() {
    	
    	System.out.printf("\n%s's accounts summary\n", this.firstName);
    	for(int i=0;i<this.accounts.size();i++) {
    		System.out.printf("  %d) %s\n", i+1, this.accounts.get(i).getSummaryLine());
    	}
    	System.out.println();
    	
    }
    
    //get no. of accounts of the user
    public int numAccounts() {
    	return this.accounts.size();    }
    
    //print transaction history of a particular account
    public void printAcctTransHistory(int acctIdx) {
    	
    	this.accounts.get(acctIdx).printTransHistory();
    	
    }
    
    //get the balance of a particular account
    public double getAcctBalance(int acctIdx) {
    	return this.accounts.get(acctIdx).getBalance();
    }
    
    //get UID of a particular account
    public String getAcctUID(int acctIdx) {
    	return this.accounts.get(acctIdx).getuID();
    }
    
    public void addAcctTransaction(int acctIdx,double amount, String memo) {
    	this.accounts.get(acctIdx).addTransaction(amount, memo);
    }
}
