package hikingapp.services.mailing;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Mailing service that does nothing. Used for testing purposes.
 */
@Service
@Profile("test")
public class NoMailService implements IMailerService{
    @Override
    public void sendMail(String emailAddress, String subject, String content) {
//        Do nothing
    }
}
