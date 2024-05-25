package hikingapp.services.validation;

import hikingapp.utils.PasswordRequestUtil;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * PasswordRequestUtil validation service.
 * Checks if the new and confirmed password corresponds.
 */
@Service
public class PasswordRequestUtilValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordRequestUtil.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var util = (PasswordRequestUtil) target;

        var newPassword = util.getNewPassword();
        var confirmPassword = util.getConfirmPassword();


        if (!newPassword.equals(confirmPassword))
            errors.rejectValue("confirmPassword", "member.password.not_matching");
    }
}
