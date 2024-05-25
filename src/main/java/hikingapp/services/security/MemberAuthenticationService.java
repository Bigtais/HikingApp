package hikingapp.services.security;

import hikingapp.data.dao.Dao;
import hikingapp.data.model.ClubMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service dedicated to the authentication of users.
 */
@Service
public class MemberAuthenticationService implements UserDetailsService {

    @Autowired
    private Dao dao;

    @Override
    public UserDetails loadUserByUsername(String email) {
        ClubMember clubMember = dao.getClubMember(email);
        if (clubMember == null)
            throw new UsernameNotFoundException(email);
        return User.withUsername(clubMember.getEmail())// Compte userName
                .password(clubMember.getPassword())// Mot de passe
                .authorities(clubMember.getAuthorities().toArray(new String[0]))// Autorisations
                .disabled(false)// Compte toujours actif
                .accountExpired(false)// Compte jamais expiré
                .accountLocked(false)// Compte jamais verrouillé
                .credentialsExpired(false)// mot de passe jamais expiré
                .build();
    }
}