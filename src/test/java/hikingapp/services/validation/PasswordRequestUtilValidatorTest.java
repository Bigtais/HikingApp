package hikingapp.services.validation;

import hikingapp.utils.PasswordRequestUtil;
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
class PasswordRequestUtilValidatorTest {

    @Autowired
    PasswordRequestUtilValidator validator;

    @Test
    void validate() {

        var passwordRequestUtil = new PasswordRequestUtil();
        passwordRequestUtil.setNewPassword("aaaaaaaa");
        passwordRequestUtil.setConfirmPassword("aaaaaaab");

        MapBindingResult result = new MapBindingResult(new HashMap<>(), "result");

        validator.validate(passwordRequestUtil, result);
        assertTrue(result.hasErrors());

        passwordRequestUtil.setConfirmPassword("aaaaaaaa");

        result = new MapBindingResult(new HashMap<>(), "result");
        validator.validate(passwordRequestUtil, result);
        assertFalse(result.hasErrors());
    }
}