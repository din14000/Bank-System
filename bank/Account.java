package bank;

import java.util.UUID;

public class Account {
	private String id = UUID.randomUUID().toString().substring(0,8);
	private double balance;

	public double getBalance() {
		return balance;
	}

	void setBalance(double balance) {
		this.balance = balance;
	}

	public String getId() {
		return id;
	}

}
