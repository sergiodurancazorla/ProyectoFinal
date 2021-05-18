package application.modelo;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import application.VariablesEstaticas;

public class CorreoElectronico extends Thread {
	private String pass = "123456789A_";

	private String destinatario;
	private String asunto;
	private String cuerpo;

	public CorreoElectronico(String destinatario, String asunto, String cuerpo) {
		this.destinatario = destinatario;
		this.asunto = asunto;
		this.cuerpo = cuerpo;
	}

	@Override
	public void run() {

		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // El servidor SMTP de Google
		props.put("mail.smtp.user", VariablesEstaticas.EMAIL_APLICACION);
		props.put("mail.smtp.clave", pass); // La clave de la cuenta
		props.put("mail.smtp.auth", "true"); // Usar autenticación mediante usuario y clave
		props.put("mail.smtp.starttls.enable", "true"); // Para conectar de manera segura al servidor SMTP
		props.put("mail.smtp.port", "587"); // El puerto SMTP seguro de Google

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(VariablesEstaticas.EMAIL_APLICACION));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			message.setSubject(asunto);
			message.setText(cuerpo);
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", VariablesEstaticas.EMAIL_APLICACION, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException me) {
			me.printStackTrace(); // Si se produce un error
		}
	}

}
