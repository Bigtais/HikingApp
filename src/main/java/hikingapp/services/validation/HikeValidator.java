package hikingapp.services.validation;

import hikingapp.data.model.Hike;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hike validation service.
 * Essentially checks if the website is in the right format and that the date is not passed.
 */
@Service
public class HikeValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Hike.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Hike hike = (Hike) target;

        if (hike.getWebsite() != null) {
            Pattern pattern = Pattern.compile("^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$");
            Matcher matcher = pattern.matcher(hike.getWebsite());

            if (!matcher.matches())
                errors.rejectValue("website", "hike.website.bad_format");
        }

        if (hike.getDate() != null)
            if (hike.getDate().before(new Date()))
                errors.rejectValue("date", "hike.date.passed");
    }
}
