package dev.oussama.blogu.controller;

import dev.oussama.blogu.services.AuthService;
import dev.oussama.blogu.web.Credentials;
import dev.oussama.blogu.web.JwtToken;
import dev.oussama.blogu.web.SignupRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j(topic = "AuthenticationController")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public JwtToken login(@Validated @RequestBody Credentials credentials) throws Exception {
        return authService.login(credentials);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public JwtToken signup(@Validated @RequestBody SignupRequest signupRequest) throws Exception {
        return authService.signup(signupRequest);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/logoff")
    public boolean logoff() {
        log.info("User logged off {}", SecurityContextHolder.getContext().getAuthentication());
        SecurityContextHolder.getContext().setAuthentication(null);
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }

}