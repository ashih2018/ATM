package ATM_0354_phase2;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;

public class EmailHandler {

    private String email, password;
    private Session session;
    private Random random;

    public EmailHandler() {
        this.email = "CSC207.bank0354@gmail.com";
        this.password = "bankdaddy1234";
        this.random = new Random();

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

    public int resetPassword(User user, String emailTo) {
        int securityNum = random.nextInt(900000) + 100000;
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("Security number for: " + user.getUsername());
            message.setText(String.valueOf(securityNum));
            Transport.send(message);
            System.out.println("DONE");
        } catch (MessagingException e) {
            System.out.println("Could't send email: " + e.toString());
            return -1;
        }
        return securityNum;
    }

}
