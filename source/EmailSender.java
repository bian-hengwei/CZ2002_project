import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender implements Notification {

	public void send(String targetAccount, String text) {

		String targetEmail = targetAccount + "@e.ntu.edu.sg";
		final String username = "cz2002group2ss12@gmail.com"; // to be added
		final String password = "CZ2002ss12group2!"; // to be added
		String subject = text.split("\n", 2)[0];
		String content = text.split("\n", 2)[1];

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(targetEmail)); // to be added an email addr
			message.setSubject(subject);
			message.setText(content);
			Transport.send(message);
			System.out.println("Email sent");
		} catch (MessagingException e) {
			System.out.println("Error: failed to send email to " + targetEmail);
			System.out.println(e);
		}
	}
}




