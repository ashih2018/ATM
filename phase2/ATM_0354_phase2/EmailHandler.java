package ATM_0354_phase2;

import javax.mail.*;
import javax.mail.internet.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Random;

public class EmailHandler {

    private static String email, password;
    private static Session session;
    private static Random random;

    public EmailHandler() {
        email = "CSC207.bank0354@gmail.com";
        password = "bankdaddy1234";
        random = new Random();
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        session = Session.getInstance(
                properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                }
        );
    }

    public static void sendEmailSummary(User user, String emailTo) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("User summary for: " + user.getUsername());
            message.setText(user.getSummary());
            Transport.send(message);
            System.out.println("Email has been sent!");
        } catch (MessagingException e) {
            System.out.println("Couldn't send email!");
        }
    }

    public static void sendLoanEmail(User user, String emailTo, LocalDateTime date, BigDecimal loan) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("Loan Payment Update");
            message.setText("You have until " + date.toString() + " to pay your loan of " + loan.doubleValue() + ".");
            Transport.send(message);
            System.out.println("Email has been sent!");
        } catch (MessagingException e) {
            System.out.println("Couldn't send email!");
        }
    }

    public static int resetPassword(Person user, String emailTo) {
        int securityNum = random.nextInt(900000) + 100000;
        return sendSecurityNumber(emailTo, securityNum, String.format("Password Reset Security Number for %s", user.getUsername()));
    }
    private static int sendSecurityNumber(String emailTo, int securityNum, String subject){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject(subject);
            message.setText(String.valueOf(securityNum));
            Transport.send(message);
            System.out.println("DONE");
        } catch (MessagingException e) {
            System.out.println("Could't send email: " + e.toString());
            return -1;
        }
        return securityNum;
    }

    public static int verifyEmail(Person user, String emailTo){
        int securityNum = random.nextInt(900000)+100000;
        return sendSecurityNumber(emailTo, securityNum, String.format("Email Verification Security Number for %s", user.getUsername()));
    }
    public static int notifyLoan(){
        return -1;
    }
}
