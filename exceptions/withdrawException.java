package exceptions;

import java.util.Date;

public class withdrawException extends Exception {
	Date timeStamp;
	int clientId;
	String clientName;
	double balance;

	public withdrawException(String message, Date timeStamp, int clientId, String clientName, double balance) {
		super(timeStamp.toLocaleString() + ": " + "Unable to withdraw money from the client " + clientName + ", id:"
				+ clientId + ". Insufficient funds. Account balance: " + balance);
		this.timeStamp = timeStamp;
		this.clientId = clientId;
		this.clientName = clientName;
		this.balance = balance;
	}
}
