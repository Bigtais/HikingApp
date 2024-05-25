package hikingapp.services.security;

import hikingapp.data.dao.Dao;
import hikingapp.data.model.ClubMember;
import hikingapp.services.providers.IPasswordEncoderProvider;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;


/**
 * Spring security configuration class.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SpringSecurity {


    @Autowired
    Dao dao;

    @Autowired
    IPasswordEncoderProvider passwordEncoderProvider;

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().requestMatchers("/webjars/**");
        };
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String[] anonymousRequests = {
                "/",
//                "/category/**",
//                "/hike/**",
//                "/register",
//                "/login",
//                "/logout",
                "/**"
        };

        String[] loggedRequests = {
                "/account",
                "/account/**",
                "/hike/edit",
        };

        String[] adminRequests = {
                "/category/create"
        };

        http.authorizeHttpRequests(config -> {//
            config.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll();
            //Pour l'admin seulement
            config.requestMatchers(adminRequests).hasAnyAuthority("ADMIN");

            // Pour les personnes authentifiées
            config.requestMatchers(loggedRequests).authenticated();

            // Pour les visiteurs
            config.requestMatchers(anonymousRequests).permitAll();

        });

        // Nous autorisons un formulaire de login
        http.formLogin(config -> {
            config.permitAll();
            config.defaultSuccessUrl("/", true);
        });

        // Nous autorisons un formulaire de logout
        http.logout(config -> {
            config.permitAll();
            config.logoutSuccessUrl("/");
            config.invalidateHttpSession(true);
        });

        // Nous activons CSRF pour les actions protégées
        http.csrf(config -> config.ignoringRequestMatchers(anonymousRequests));

        return http.build();
    }

    /*
     * Définition de l'admin en BD.
     */
    @PostConstruct
    public void init() {
        var encoder = passwordEncoderProvider.getPasswordEncoder();
        var admin = new ClubMember();
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setEmail("admin@admin.com");
        admin.setPassword(encoder.encode("administrator"));
        admin.setAuthorities(List.of("ADMIN", "USER"));

        dao.addClubMember(admin);

        System.out.println("--- INIT SPRING SECURITY");
    }

    /*
     * Définir le fournisseur d'authentification. Nous utilisons la version
     * DaoAuthenticationProvider qui récupère les informations à partir du
     * UserDetailsService que nous avons défini avant.
     */
    @Bean
    public AuthenticationProvider myAuthenticationProvider(//
                                                           @Autowired PasswordEncoder encoder, //
                                                           @Autowired UserDetailsService userDetailsService) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }
}