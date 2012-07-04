import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EnviaEmail {

	public boolean enviaEmailDestinatario(String emailDestinatario){
		
		Properties p = new Properties();  

		p.put("mail.user", "jeacarvalho@gmail.com");
		p.put("mail.from", "jeacarvalho@gmail.com");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.port", "465"); // smtp port

        Authenticator auth = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("jeacarvalho", "0123Osrevdm**");
            }
        };



		
	   Session session = Session.getDefaultInstance(p, null);  
	   MimeMessage msg = new MimeMessage(session);  

	   try {
			// "de" e "para"!!
			msg.setFrom(new InternetAddress("jeacarvalho@gmail.com"));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailDestinatario));

			// nao esqueca da data!
			// ou ira 31/12/1969 !!!
			msg.setSentDate(new Date());

			msg.setSubject("assunto da mensagem");

			msg.setText("corpo da mensagem");

			// evniando mensagem (tentando)
			Transport.send(msg);
		}
		catch (AddressException e) {
			System.out.println("Endereço de email inválido");
			e.printStackTrace();
		}
		catch (MessagingException e) {
			System.out.println("Alguma excecao de mensagem: ");
			e.printStackTrace();
		}

	   
	   
		return true;
	}
}
