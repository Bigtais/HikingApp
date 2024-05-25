package hikingapp.services.providers;

import hikingapp.data.model.ClubMember;

import java.security.Principal;

/**
 * General interface for club member search and update operation.
 */
public interface IClubMemberProvider {

    /**
     * Returns the club member associated to the mail if exists.
     * @param email The mail of the club member to search for.
     * @return The found club member, null otherwise.
     */
    ClubMember getClubMember(String email);

    /**
     * Returns the club member associated to the currently authenticated user.
     * @param principal The principal object corresponding to the currently authenticated user.
     * @return The found club member, null otherwise.
     */
    ClubMember getCurrentAuthenticatedMember(Principal principal);

    /**
     * Returns the club member associated to the currently authenticated user with its associated hikes.
     * @param principal The principal object corresponding to the currently authenticated user.
     * @return The found club member with its hikes, null otherwise.
     */
    ClubMember getCurrentAuthenticatedMemberWithHikes(Principal principal);

    /**
     * Register (creates) a club member.
     * @param member The club member to register.
     */
    void registerUser(ClubMember member);

    /**
     * Updates the password of the currently authenticated user.
     * @param principal The principal object corresponding to the currently authenticated user.
     * @param password The new password of the user.
     * @return The updated club member.
     */
    ClubMember updateClubMemberPassword(Principal principal, String password);

    /**
     * Updates the password of the user associated to the email.
     * @param email The email of the user.
     * @param password The new password of the user.
     * @return The updated club member.
     */
    ClubMember updateClubMemberPassword(String email, String password);

    /**
     * Checks if the password of the currently authenticated user is valid.
     * @param principal The principal object corresponding to the currently authenticated user.
     * @param password The password to check.
     * @return True if the password is correct, false otherwise.
     */
    boolean isPasswordValid(Principal principal, String password);
}
