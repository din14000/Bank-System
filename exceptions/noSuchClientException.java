package exceptions;

import java.util.Date;

public class noSuchClientException extends Exception {
	public noSuchClientException(String message, Date timeStamp, int clientId) {
		super(timeStamp.toLocaleString() + ": " + "Unable to find the requested client. The id provided(" + clientId
				+ ") does not exist in the system");

	}
}
