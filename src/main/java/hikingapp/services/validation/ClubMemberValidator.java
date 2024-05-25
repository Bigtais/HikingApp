package hikingapp.services.validation;


import hikingapp.data.dao.Dao;
import hikingapp.data.model.ClubMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Club member validation service.
 * Essentially checks if the email address is in the right format and that is not already in use.
 */
@Service
public class ClubMemberValidator implements Validator {

    @Autowired
    Dao dao;

    @Override
    public boolean supports(Class<?> clazz) {
        return ClubMember.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ClubMember member = (ClubMember) target;

        if (member.getEmail() != null) {

            member.setEmail(
                    member.getEmail().toLowerCase()
            );

            if (!member.getEmail().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"))
                errors.rejectValue("email", "member.email.bad_email");

            ClubMember inBase = dao.getClubMember(member.getEmail());
            if (inBase != null)
                errors.rejectValue("email", "member.email.already_used");
        }
    }
}
