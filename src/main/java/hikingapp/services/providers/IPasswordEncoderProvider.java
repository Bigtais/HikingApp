package hikingapp.services.providers;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * General interface for providing a password encoder.
 */
public interface IPasswordEncoderProvider {

    /**
     * Returns a password encoder.
     * @return A password encoder.
     */
    PasswordEncoder getPasswordEncoder();
}
