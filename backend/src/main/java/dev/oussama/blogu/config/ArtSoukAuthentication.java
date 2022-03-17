package dev.oussama.blogu.config;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
public class ArtSoukAuthentication implements Authentication {

    private boolean authenticated;
    private Collection<? extends GrantedAuthority> authorities;
    private String credentials;
    private String principal;
    private String details;
    private String name;

    public ArtSoukAuthentication(Authentication authentication) {
        this.principal = ((User) authentication.getPrincipal()).getUsername();
        this.credentials = authentication.getCredentials().toString();
        this.authenticated = authentication.isAuthenticated();
        this.authorities = ((User) authentication.getPrincipal()).getAuthorities();
        this.name = authentication.getName();
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (this.authenticated)
            throw new IllegalArgumentException("Already authenticated");
        this.authenticated = isAuthenticated;
    }
}
