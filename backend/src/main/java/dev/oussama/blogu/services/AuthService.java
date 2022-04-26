package dev.oussama.blogu.services;

import dev.oussama.blogu.config.JwtUtil;
import dev.oussama.blogu.model.Role;
import dev.oussama.blogu.model.User;
import dev.oussama.blogu.repository.RoleRepository;
import dev.oussama.blogu.repository.UserRepository;
import dev.oussama.blogu.web.Credentials;
import dev.oussama.blogu.web.JwtToken;
import dev.oussama.blogu.web.SignupRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RoleRepository roleRepository;

    //@formatter:off
    public AuthService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder,
        JwtUtil jwtUtil,
        RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.roleRepository = roleRepository;
    }
    //@formatter:on

    public JwtToken signup(SignupRequest signupRequest) throws Exception {
        final String login = signupRequest.getLogin();
        final String email = signupRequest.getEmail();
        final String password = signupRequest.getSecret();

        log.info("Signin up " + login);

        if (existsByUsername(login))
            throw new BadCredentialsException("user with login '" + login + "' already exists! Did you forget your credentials?");

        if (existsByEmail(email))
            throw new BadCredentialsException("user with email '" + email + "' already exists! Did you forget your credentials?");

        User user = new User();

        final Credentials credentials = new Credentials(login, passwordEncoder.encode(password));

        user.setCredentials(credentials);
        user.setEmail(email);

        final Role role = roleRepository.findByName("ROLE_" + signupRequest.getRole().name()).orElseThrow();

        user.setRoles(List.of(role));

        log.info("Saving user " + userRepository.save(user));

        return login(new Credentials(login, password));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByCredentials_Username(username);
    }

    public JwtToken login(Credentials credentials) throws Exception {
        log.info("Logging in {}", credentials.getUsername());

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(auth);

        log.info("Setting security context auth to {}", auth);

        final String token = jwtUtil.createTokenFromUserDetails(user);

        return new JwtToken(token);
    }
}
