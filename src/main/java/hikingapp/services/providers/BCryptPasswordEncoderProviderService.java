package hikingapp.services.providers;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * BCryptPasswordEncoder provider service. Provide a BCryptPasswordEncoder object.
 */
@Service
public class BCryptPasswordEncoderProviderService implements IPasswordEncoderProvider {
    @Bean
    @Override
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
