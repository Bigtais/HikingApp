package hikingapp.utils;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

/**
 * Random string generator of length 10 with alphanumeric characters.
 */
@Service
@Profile("!test")
@Primary
public class RandomStringGenerator implements StringGenerator {
    @Override
    public String getGeneratedString() {
        org.apache.commons.text.RandomStringGenerator generator = org.apache.commons.text.RandomStringGenerator.builder()
                .withinRange('0', 'z')
                .filteredBy(LETTERS, DIGITS)
                .build();

        return generator.generate(10);
    }
}
