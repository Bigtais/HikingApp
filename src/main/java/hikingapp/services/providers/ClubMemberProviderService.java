package hikingapp.services.providers;

import hikingapp.data.dao.Dao;
import hikingapp.data.model.ClubMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * Service dedicated to the search of club members.
 */
@Service
public class ClubMemberProviderService implements IClubMemberProvider {

    @Autowired
    Dao dao;

    @Autowired
    IPasswordEncoderProvider passwordEncoderProvider;

    public ClubMember getClubMember(String email) {
        return dao.getClubMember(email);
    }

    public ClubMember getCurrentAuthenticatedMember(Principal principal) {
        return dao.getClubMember(principal.getName());
    }

    public ClubMember getCurrentAuthenticatedMemberWithHikes(Principal principal) {
        var clubMember = getCurrentAuthenticatedMember(principal);
        return dao.getClubMemberWithHikesDetails(clubMember.getId());
    }

    public void registerUser(ClubMember member) {
//        Encoding password
        var encoder = passwordEncoderProvider.getPasswordEncoder();
        var encodedPassword = encoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

//        Transform e-mail to lower case to prevent issues
        member.setEmail(
                member.getEmail().toLowerCase()
        );

        dao.addClubMember(member);
    }

    @PreAuthorize("isAuthenticated()")
    public ClubMember updateClubMemberPassword(Principal principal, String password) {
        var clubMember = getCurrentAuthenticatedMember(principal);
        var encoder = passwordEncoderProvider.getPasswordEncoder();
        clubMember.setPassword(encoder.encode(password));
        return dao.updateClubMember(clubMember);
    }

    @Override
    public ClubMember updateClubMemberPassword(String email, String password) {
        var member = dao.getClubMember(email);
        var encoder = passwordEncoderProvider.getPasswordEncoder();
        member.setPassword(encoder.encode(password));
        return dao.updateClubMember(member);
    }

    public boolean isPasswordValid(Principal principal, String password) {
        var clubMember = getCurrentAuthenticatedMember(principal);
        var encoder = passwordEncoderProvider.getPasswordEncoder();
        return encoder.matches(password, clubMember.getPassword());
    }
}
