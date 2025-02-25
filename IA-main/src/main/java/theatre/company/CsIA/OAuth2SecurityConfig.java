package theatre.company.CsIA;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

@Configuration
public class OAuth2SecurityConfig {

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper(
        @Value("${admin.emails:}") String[] adminEmails // Safe default (empty array)
    ) {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            for (GrantedAuthority authority : authorities) {
                // Check if the authority is from an OIDC user (e.g., Google)
                if (authority instanceof OidcUserAuthority oidcUserAuthority) {
                    String email = oidcUserAuthority.getIdToken().getEmail().toLowerCase();

                    // Check against admin emails (trimmed and case-insensitive)
                    for (String adminEmail : adminEmails) {
                        if (adminEmail != null && adminEmail.trim().equalsIgnoreCase(email)) {
                            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                            break;
                        }
                    }
                }
            }

            return mappedAuthorities;
        };
    }
    
}