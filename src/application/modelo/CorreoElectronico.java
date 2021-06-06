package application.modelo;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import application.VariablesEstaticas;

public class CorreoElectronico extends Thread {

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
		props.put("mail.smtp.clave", VariablesEstaticas.PASS); // La clave de la cuenta
		props.put("mail.smtp.auth", "true"); // Usar autenticación mediante usuario y clave
		props.put("mail.smtp.starttls.enable", "true"); // Para conectar de manera segura al servidor SMTP
		props.put("mail.smtp.port", "587"); // El puerto SMTP seguro de Google

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(VariablesEstaticas.EMAIL_APLICACION, "Gestion Incidencias"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			message.setSubject(asunto);
			message.setText(cuerpo);
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", VariablesEstaticas.EMAIL_APLICACION, VariablesEstaticas.PASS);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException me) {
			VariablesEstaticas.log.logGeneral("[ERROR]No se ha podido enviar el email\n\t" + me.toString());
		} catch (UnsupportedEncodingException e) {
			VariablesEstaticas.log.logGeneral("[ERROR]No se ha podido enviar el email\n\t" + e.toString());
		}
	}

}
