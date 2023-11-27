package co.edu.unbosque.util;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

	private static String origen = "uscholarships2023@gmail.com";
	private static String contra = "pkltaudiacfcfwug";
	private Properties p;
	private static Session session;
	private static MimeMessage mimeMessage;

	public Email() {
		p = new Properties();
	}

	private void configuration() {
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		p.setProperty("mail.smtp.starttls.enable", "true");
		p.setProperty("mail.smtp.port", "587");
		p.setProperty("mail.smtp.user", origen);
		p.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		p.setProperty("mail.smtp.auth", "true");
	}

	public void sendMessage(String destino, String asunto, String txt) {
		configuration();
		session = Session.getDefaultInstance(p);
		mimeMessage = new MimeMessage(session);

		try {
			mimeMessage.setFrom(new InternetAddress(origen));
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(destino));
			mimeMessage.setSubject(asunto);
			mimeMessage.setText(txt, "ISO-8859-1", "html");
		} catch (AddressException e) {
			Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, e);
		} catch (MessagingException e) {
			Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, e);
		}
		
		try {
			Transport t = session.getTransport("smtp");
			t.connect(origen, contra);
			t.sendMessage(mimeMessage, mimeMessage.getRecipients(Message.RecipientType.TO));
			t.close();
		} catch (NoSuchProviderException e) {
			Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, e);
		} catch (MessagingException e) {
			Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, e);
		}

	}
}
