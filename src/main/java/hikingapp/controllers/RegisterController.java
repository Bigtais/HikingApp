package hikingapp.controllers;

import hikingapp.data.model.ClubMember;
import hikingapp.services.providers.IClubMemberProvider;
import hikingapp.services.validation.ClubMemberValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the registration of users.
 */
@Controller
@RequestMapping("/register")
public class RegisterController {


    @Autowired
    ClubMemberValidator validator;

    @Autowired
    IClubMemberProvider clubMemberProvider;

    /**
     * Shows the club member registration form.
     * @param clubMember The club member to create.
     * @return The club member creation form.
     */
    @GetMapping
    public String getRegisterForm(@ModelAttribute ClubMember clubMember) {
        return "registerForm";
    }

    /**
     * Checks if the input club member is valid and tries to register it.
     * @param clubMember The club member to register.
     * @param result The result of the validation of the club member.
     * @return A redirection to the home page if successful,
     * else the registration form with the errors found.
     */
    @PostMapping
    public String registerMember(@ModelAttribute @Valid ClubMember clubMember, BindingResult result) {
        validator.validate(clubMember, result);
        if (result.hasErrors())
            return "registerForm";
        clubMemberProvider.registerUser(clubMember);
        return "redirect:/";
    }


}
