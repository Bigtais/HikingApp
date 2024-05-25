package hikingapp.services.mailing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

/**
 * Implementation of the mailing service with actual mail sending.
 */
@Service
@Profile("!test")
@Primary
public class MailerServiceImpl implements IMailerService{

    @Autowired
    Environment environment;

    @Override
    public void sendMail(String emailAddress, String subject, String content) {

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("localhost");

        javaMailSender.setPort(10025);

        var message = new SimpleMailMessage();

        message.setFrom("noreply@hikingapp.com");
        message.setTo(emailAddress);
        message.setSubject(subject);
        message.setText(content);

        javaMailSender.send(message);
    }
}
