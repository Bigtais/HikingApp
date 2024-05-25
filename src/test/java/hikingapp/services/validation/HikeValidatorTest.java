package hikingapp.services.validation;

import hikingapp.data.model.Hike;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.MapBindingResult;

import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class HikeValidatorTest {

    @Autowired
    HikeValidator validator;

    @Test
    void validateWebsite() {

        var date = new Date(System.currentTimeMillis() + 10_000);

        var hike = new Hike();
        hike.setName("Name 1");
        hike.setDescription("Name 1");
        hike.setDate(date);
        hike.setWebsite("Name 1");

        MapBindingResult result = new MapBindingResult(new HashMap<>(), "result");

        validator.validate(hike, result);
        assertTrue(result.hasErrors());


        hike.setWebsite("hs://name.com");

        result = new MapBindingResult(new HashMap<>(), "result");
        validator.validate(hike, result);
        assertTrue(result.hasErrors());


        hike.setWebsite("name.com");

        result = new MapBindingResult(new HashMap<>(), "result");
        validator.validate(hike, result);
        assertFalse(result.hasErrors());


        hike.setWebsite("www.name.com");

        result = new MapBindingResult(new HashMap<>(), "result");
        validator.validate(hike, result);
        assertFalse(result.hasErrors());


        hike.setWebsite("www://name.com");

        result = new MapBindingResult(new HashMap<>(), "result");
        validator.validate(hike, result);
        assertTrue(result.hasErrors());


        hike.setWebsite("http://name.com");

        result = new MapBindingResult(new HashMap<>(), "result");
        validator.validate(hike, result);
        assertFalse(result.hasErrors());


        hike.setWebsite("https://name.com");

        result = new MapBindingResult(new HashMap<>(), "result");
        validator.validate(hike, result);
        assertFalse(result.hasErrors());
    }

    @Test
    void validateDate() {
        var date = new Date(0); // 01/01/1970

        var hike = new Hike();
        hike.setName("Name 1");
        hike.setDescription("Name 1");
        hike.setWebsite("https://name.com");
        hike.setDate(date);

        MapBindingResult result = new MapBindingResult(new HashMap<>(), "result");
        validator.validate(hike, result);
        assertTrue(result.hasErrors());

        date = new Date(System.currentTimeMillis() + 100_000);
        hike.setDate(date);

        result = new MapBindingResult(new HashMap<>(), "result");
        validator.validate(hike, result);
        assertFalse(result.hasErrors());

    }
}