package dev.oussama.blogu.services;

import dev.oussama.blogu.model.User;
import dev.oussama.blogu.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username : {}", username);

        User user = userRepository.findByCredentials_Username(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        log.info(user.toString());

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(username)
                .password(user.getPassword())
                .authorities(user.getRoles())
                .accountExpired(false)
                .disabled(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .build();
    }
}
