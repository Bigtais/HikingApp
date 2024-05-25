package hikingapp.utils;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Test string generator. Always generates the "test" string. Used for testing purposes.
 */
@Service
@Profile("test")
public class TestStringGenerator implements StringGenerator {
    @Override
    public String getGeneratedString() {
        return "test";
    }
}
