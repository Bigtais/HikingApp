package hikingapp.controllers;

import hikingapp.services.mailing.IMailerService;
import hikingapp.services.providers.IClubMemberProvider;
import hikingapp.services.providers.IPasswordEncoderProvider;
import hikingapp.utils.StringGenerator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for the recovery of a lost password.
 */
@Controller
@RequestMapping("/password-forgot")
public class LoginForgotController {

    @Autowired
    IMailerService mailerService;

    @Autowired
    IClubMemberProvider clubMemberProvider;

    @Autowired
    IPasswordEncoderProvider passwordEncoderProvider;

    @Autowired
    StringGenerator stringGenerator;

    /**
     * Shows the password forgot form.
     * @return The password forgot form.
     */
    @GetMapping("/ask-mail")
    public String getPasswordForgotForm() {
        return "passwordForgotForm";
    }

    /**
     * Sends a mail to the requested address if the user associated to this mail exists.
     * @param session The current session.
     * @param email The email address to search for.
     * @return A redirection to the code request page, else the form if the user is not found.
     */
    @PostMapping("/ask-mail")
    public String recoverPassword(HttpSession session, @RequestParam String email) {
        var member = clubMemberProvider.getClubMember(email);
        if (member == null)
            return "passwordForgotForm";

        var generated = stringGenerator.getGeneratedString();
        mailerService.sendMail(email, "Password Recovery", "The reset code is: " + generated);

        var encoder = passwordEncoderProvider.getPasswordEncoder();
        session.setAttribute("resetCodeEncoded", encoder.encode(generated));
        session.setAttribute("email", email);

        return "redirect:ask-code";
    }

    /**
     * Shows the code received by mail request form.
     * @param session The current session
     * @return The code request form, or a redirection to the mail request form if the code has not been set.
     */
    @GetMapping("/ask-code")
    public String getReceivedCodeForm(HttpSession session) {
        if (session.getAttribute("resetCodeEncoded") == null)
            return "redirect:ask-mail";
        return "receivedCodeForm";
    }

    /**
     * Checks if the input code corresponds to the code generated.
     * If the code corresponds, the password of the user is changed to that code.
     * @param session The current session.
     * @param code The input code.
     * @return The code request page if the code is incorrect,
     * else a redirection to the mail request page if the code or email have not been set,
     * or a redirection to the home page if successful.
     */
    @PostMapping("/ask-code")
    public String checkCorrectCode(HttpSession session, @RequestParam String code) {
        var sessionEncodedCode = session.getAttribute("resetCodeEncoded");
        if (sessionEncodedCode == null)
            return "redirect:ask-mail";

        var encoder = passwordEncoderProvider.getPasswordEncoder();
        if (!encoder.matches(code, sessionEncodedCode.toString()))
            return "receivedCodeForm";

        var email = session.getAttribute("email").toString();

        if (email == null)
            return "redirect:ask-mail";

        clubMemberProvider.updateClubMemberPassword(email, code);

        session.removeAttribute("resetCodeEncoded");

        return "redirect:/";
    }

}
