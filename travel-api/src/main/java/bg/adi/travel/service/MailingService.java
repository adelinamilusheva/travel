package bg.adi.travel.service;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

@Service
public class MailingService {
    private final Logger LOGGER = LoggerFactory.getLogger(MailingService.class);

    private final Properties props;

    @Value("${mail.smtp.username}")
    private String username;

    @Value("${mail.smtp.password}")
    private String password;

    public MailingService() {
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
    }

    public void send(List<String> recipients, String subject, String message) {
        sendMail(recipients, subject, message);
    }

    private void sendMail(List<String> recipients, String subject, String message) {
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Message simpleMessage = new MimeMessage(session);
        try {
            simpleMessage.setFrom(new InternetAddress(username, "Sport.com"));
            simpleMessage.setSubject(subject);

            simpleMessage.addRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(joinRecipients(recipients)));

            MimeBodyPart messageBody = new MimeBodyPart();
            messageBody.setContent(message, "text/html; charset=UTF-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBody);
            simpleMessage.setContent(multipart);

            Transport.send(simpleMessage);
            LOGGER.info("An email has been sent to {}", recipients);
        } catch (MessagingException | UnsupportedEncodingException e) {
            LOGGER.info("An email has not been sent to {}", recipients);
        }
    }

    private String joinRecipients(List<String> recipients) { return StringUtils.join(recipients, ","); }
}
