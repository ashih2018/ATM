package ATM_0354_phase2;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailHandler {

    private String email, password;
    private Session session;

    public EmailHandler() {
        //TODO: create an email to use
        this.email = "_________@gmail.com";
        this.password = "________";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        this.session = Session.getInstance(
                properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                }
        );
    }

    public void sendEmailSummary(User user, String emailTo) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("User summary for: " + user.getUsername());
            message.setText(user.getSummary());
            Transport.send(message);
            System.out.println("DONE");
        } catch (MessagingException e) {
            System.out.println("Could't send email: " + e.toString());
        }
    }

}
