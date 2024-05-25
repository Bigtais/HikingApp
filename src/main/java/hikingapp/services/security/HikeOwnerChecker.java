package hikingapp.services.security;

import hikingapp.data.model.Hike;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * Service dedicated to the verification of the owner of a hike.
 */
@Service("hikeOwnerChecker")
public class HikeOwnerChecker {

    /**
     * Checks if the currently authenticated user is the owner of the hike.
     * @param principal The UserDetails object corresponding to the currently authenticated user.
     * @param hike The hike to which to check the creator.
     * @return true if the user is the owner of the hike, false otherwise.
     */
    public boolean isOwner(UserDetails principal, Hike hike){
        return principal.getUsername().equalsIgnoreCase(hike.getCreator().getEmail());
    }

    /**
     * Checks if the currently authenticated user is the owner of the hike.
     * @param principal The Principal object corresponding to the currently authenticated user.
     * @param hike The hike to which to check the creator.
     * @return true if the user is the owner of the hike, false otherwise.
     */
    public boolean isOwner(Principal principal, Hike hike){
        return principal.getName().equalsIgnoreCase(hike.getCreator().getEmail());
    }

}
