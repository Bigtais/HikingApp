package hikingapp.services.mailing;

/**
 * General interface for the sending of mails.
 */
public interface IMailerService {

    /**
     * Sends a mail to the mail address with the subject and content.
     * @param emailAddress The mail address to which the mail should be sent.
     * @param subject The subject of the mail.
     * @param content The content of the mail.
     */
    void sendMail(String emailAddress, String subject, String content);

}
