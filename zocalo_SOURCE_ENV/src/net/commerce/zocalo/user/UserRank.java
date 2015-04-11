package net.commerce.zocalo.user;

import net.commerce.zocalo.currency.Quantity;

public class UserRank 
{
	String  userName;
    Quantity balance;
    int rank;
    
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Quantity getBalance() {
		return balance;
	}
	public void setBalance(Quantity balance) {
		this.balance = balance;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
    
	public String toString()
	{
		return rank+"\t"+userName+"\t"+balance;
				
				
	}

}
