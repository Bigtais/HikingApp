package hikingapp.controllers;

import hikingapp.services.providers.ClubMemberProviderService;
import hikingapp.services.providers.IPasswordEncoderProvider;
import hikingapp.services.validation.PasswordRequestUtilValidator;
import hikingapp.utils.PasswordRequestUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * Controller for displaying account details and operations.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    ClubMemberProviderService clubMemberProviderService;

    @Autowired
    IPasswordEncoderProvider passwordEncoderProvider;

    @Autowired
    PasswordRequestUtilValidator validator;

    /**
     * Show the account details of the current authenticated user.
     * @param principal The principal object of the currently authenticated user.
     * @return A view with the account details.
     */
    @RequestMapping("")
    public ModelAndView getAccountDetails(Principal principal) {
        var member = clubMemberProviderService.getCurrentAuthenticatedMemberWithHikes(principal);
        return new ModelAndView("member", "member", member);
    }

    /**
     * Show the password edition form.
     * @param passwordChange The initialised PasswordRequestUtil object.
     * @return The view corresponding to the password edition form.
     */
    @GetMapping("/password-edit")
    public String getPasswordEditForm(@ModelAttribute PasswordRequestUtil passwordChange) {
        return "passwordEditForm";
    }

    /**
     * Performs a update of the password of the currently authenticated user.
     * @param principal The principal object of the currently authenticated user.
     * @param passwordChange The PasswordRequestUtil object containing the old, new and confirmed password.
     * @param result The result of the validation of the PasswordRequestUtil object.
     * @return A redirect if the operation is successful, else the password edition form with the errors found.
     */
    @PostMapping("/password-edit")
    public String updatePassword(Principal principal,
                                 @ModelAttribute @Valid PasswordRequestUtil passwordChange,
                                 BindingResult result) {
        validator.validate(passwordChange, result);
        var oldPassword = passwordChange.getOldPassword();
        var newPassword = passwordChange.getNewPassword();

        if (!clubMemberProviderService.isPasswordValid(principal, oldPassword)) {
            result.rejectValue("oldPassword", "member.password.incorrect");
        }

        if (result.hasErrors())
            return "passwordEditForm";

        clubMemberProviderService.updateClubMemberPassword(principal, newPassword);
        return "redirect:/account";
    }

    /**
     * Initialises the inputs of the form with empty values.
     * @return The empty model attribute.
     */
    @ModelAttribute
    public PasswordRequestUtil newPassword()
    {
        PasswordRequestUtil passwordRequestUtil = new PasswordRequestUtil();
        passwordRequestUtil.setOldPassword("");
        passwordRequestUtil.setNewPassword("");
        passwordRequestUtil.setConfirmPassword("");
        return passwordRequestUtil;
    }

}
