package src.boundary;


/**
 * Notification interface of sending any notification to student
 */
public interface Notification {

    /**
     * Sends text to target account
     * @param targetAccount the account we are sending to
     * @param text          the content
     */
	public void send(String targetAccount, String text); 

}
