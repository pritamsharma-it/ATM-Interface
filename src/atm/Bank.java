package atm;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
	private String name;
	private ArrayList<User> users;
	private ArrayList<Account> accounts;
	
	//create a bank constructor
	public Bank(String name) {
		
		this.name=name;
		this.users=new ArrayList<User>();
		this.accounts=new ArrayList<Account>();
		
	}
	
	//get unique ID for every user
	public String getNewUserUID() {
		
		//initialize
		String uID;
		Random rng = new Random();
		int len=5;
		boolean nonUnique;
		
		//continue loop until we get a new unique ID
		do {
			
			//generate ID
			uID = "";
			for(int i=0;i<len;i++) {
				uID += ((Integer)rng.nextInt(10)).toString();
			}
			
			//check if its unique
			nonUnique=false;
			for(User u: this.users) {
				if(uID.compareTo(u.getuID()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		}while(nonUnique);
		
		return uID;
		
	}

	//get unique id for every account
	public String getNewAccountUID() {
		//initialize
		String uID;
		Random rng = new Random();
		int len=10;
		boolean nonUnique;
		
		//continue loop until we get a new unique ID
		do {
			
			//generate ID
			uID = "";
			for(int i=0;i<len;i++) {
				uID += ((Integer)rng.nextInt(10)).toString();
			}
			
			//check if its unique
			nonUnique=false;
			for(Account a: this.accounts) {
				if(uID.compareTo(a.getuID()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		}while(nonUnique);
		
		return uID;
	}
	
	//add and account
	public void addAccount(Account onAcct) {
		this.accounts.add(onAcct);
	}
	
	public User addUser(String firstName, String lastName, String pin) {
		
		//create new user object and add it to our list
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		//create a savings account for user and add the user and bank accounts lists
		Account newAccount=new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);
		
		return newUser;
	}
	
	public User userlogin(String userID,String pin) {
	
		//search thought list of users
		for(User u: this.users) {
			
			//check user ID is correct
			if(u.getuID().compareTo(userID)==0 && u.validatePin(pin)) {
				return u;
			}
			
		}
		
		//if we hadn't found a user or the pin isn't valid
		return null;
		
	}
	
	public String getName() {
		return this.name;
	}
}
