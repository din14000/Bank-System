package exceptions;

import java.util.Date;

public class deleteException extends Exception {
	Date timeStamp;
	int clientId;
	String clientName;
	double balance;

	public deleteException(String message, Date timeStamp, int clientId, String clientName, double balance) {
		super(timeStamp.toLocaleString() + ": " + "Unable to delete client " + clientName + ", id:" + clientId
				+ " due to negative account balance. Account balance: " + balance + "$");
		this.timeStamp = timeStamp;
		this.clientId = clientId;
		this.clientName = clientName;
		this.balance = balance;

	}

}
