package hikingapp.services.validation;

import hikingapp.data.model.ClubMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.MapBindingResult;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class ClubMemberValidatorTest {

    @Autowired
    ClubMemberValidator validator;

    @Test
    void validate() {
        var clubMember = new ClubMember();
        clubMember.setFirstName("aaa");
        clubMember.setLastName("aaa");
        clubMember.setPassword("aaaaaaaa");
        clubMember.setEmail("aaagmail.com");

        MapBindingResult result = new MapBindingResult(new HashMap<>(), "result");

        validator.validate(clubMember, result);
        assertTrue(result.hasErrors());

        clubMember.setEmail("@gmail.com");

        result = new MapBindingResult(new HashMap<>(), "result");
        validator.validate(clubMember, result);
        assertTrue(result.hasErrors());

        clubMember.setEmail("@gmail.a");

        result = new MapBindingResult(new HashMap<>(), "result");
        validator.validate(clubMember, result);
        assertTrue(result.hasErrors());

        clubMember.setEmail("aaa@gmail.com");

        result = new MapBindingResult(new HashMap<>(), "result");
        validator.validate(clubMember, result);
        assertFalse(result.hasErrors());
    }
}